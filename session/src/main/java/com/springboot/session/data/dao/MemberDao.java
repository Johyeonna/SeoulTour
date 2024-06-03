package com.springboot.session.data.dao;


import com.springboot.session.data.entity.Member;
import com.springboot.session.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDao{
    private final MemberRepository memberRepository;
    public Member createMember(Member member){
        return memberRepository.save(member);
    }
    public Member findByUserId(String userid){
        Optional<Member> findedMember =  memberRepository.findByUserid(userid);
        if(findedMember.isPresent())
            return findedMember.get();
        else
            return null;
    }
}
