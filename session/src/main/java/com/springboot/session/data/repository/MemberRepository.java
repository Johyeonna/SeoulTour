package com.springboot.session.data.repository;

import com.springboot.session.data.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUserid(String userId);
}
