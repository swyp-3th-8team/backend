package com.swig.manda.controller;

import com.swig.manda.dto.LoginRequest;
import com.swig.manda.dto.MailDto;
import com.swig.manda.dto.MemberDto;
import com.swig.manda.dto.PWupdateDto;
import com.swig.manda.model.Member;
import com.swig.manda.repository.MemberRepository;
import com.swig.manda.service.MemberService;
import com.swig.manda.service.SendMailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/member")
public class LoginController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MemberService memberService;
    @Autowired
    SendMailService sendMailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 이건 중복 처리 부분임.
    @GetMapping("/duplicate")
    public ResponseEntity<Map<String, Boolean>> duplicate(@RequestParam("username") String username) {
        boolean isDupplicate = memberService.duplicateUsername(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("중복입니다!", isDupplicate);
        return ResponseEntity.ok(response);
    }

    // 비밀번호 찾기 요청
    @PostMapping("/check/findPw")
    public ResponseEntity<String> handleFindPasswordRequest(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String username = requestData.get("username");
        try {
            sendMailService.sendResetPasswordEmail(email, username);
            return ResponseEntity.ok("비밀번호 재설정 이메일을 성공적으로 발송했습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("비밀번호 재설정 이메일 발송에 실패했습니다.");
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().body("User authenticated successfully");
    }

    // 비밀번호 업데이트 요청!!
    @PostMapping("/pwUpdate")
    public ResponseEntity<String> updatePassword(@RequestBody PWupdateDto pwUpdateDto) {
        try {
            if (!pwUpdateDto.getNewPassword().equals(pwUpdateDto.getNewRepassword())) {
                return ResponseEntity.badRequest().body("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }

            Member member = memberRepository.findByUsername(pwUpdateDto.getUsername());
            String currentPassword = memberRepository.findPasswordByUsername(pwUpdateDto.getUsername());

            if (member == null || !bCryptPasswordEncoder.matches(pwUpdateDto.getPassword(), currentPassword)) {
                return ResponseEntity.badRequest().body("현재 비밀번호가 정확하지 않습니다.");
            }

            memberService.updatePassword(pwUpdateDto.getUsername(), pwUpdateDto.getNewPassword());
            return ResponseEntity.ok("비밀번호가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("비밀번호 업데이트 중 오류가 발생했습니다.");
        }
    }
}
