package com.swig.manda.repository;

import com.swig.manda.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;


@Transactional
public interface MemberRepository extends JpaRepository<Member,Long> {


    Member findByUserId(String userId);
    Member findByEmail(String email);


    Boolean existsByUserId(String userId);


    @Query("SELECT m.password FROM Member m WHERE m.userId = :userId")
    String findPasswordByUserId(@Param("userId") String userId);

    Optional<Member> findByEmailAndUsername(String email, String username);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :password WHERE m.userId= :userId")
    void updatePasswordByUserId(@Param("password") String password, @Param("userId") String userId);


}

