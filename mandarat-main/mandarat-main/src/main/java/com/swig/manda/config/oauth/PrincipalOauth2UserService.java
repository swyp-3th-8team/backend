package com.swig.manda.config.oauth;

import com.swig.manda.config.auth.PrincipalDetails;
import com.swig.manda.model.Member;
import com.swig.manda.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.swig.manda.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {



    private static final Logger logger = LoggerFactory.getLogger(PrincipalOauth2UserService.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        String username = provider + "_" + providerId;

        Member userEntity = memberRepository.findByUsername(username);
        if (userEntity == null) {
            userEntity = memberService.registerNewOAuth2User(provider, providerId, username);
            logger.info("새로운 구글 로그인: {}", username);
        } else {
            logger.info("에잇 여기 등록되있자나: {}", username);
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}
