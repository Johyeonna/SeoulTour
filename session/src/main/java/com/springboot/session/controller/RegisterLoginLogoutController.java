package com.springboot.session.controller;

import com.springboot.session.data.AuthType;
import com.springboot.session.data.dto.MemberRequestDto;
import com.springboot.session.data.entity.Member;
import com.springboot.session.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterLoginLogoutController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setName(memberRequestDto.getName());
        member.setUserid(memberRequestDto.getUserid());
        member.setPassword(memberRequestDto.getPassword());
        member.setEmail(memberRequestDto.getEmail());
        member.setAuthType(AuthType.USER);

        Map<String, String> response = new HashMap<>();
        if (memberService.hasUserId(member.getUserid())) {
            response.put("status", "error");
            response.put("message", "아이디가 이미 존재합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            Member registerdMember = memberService.createMember(member);
            if (registerdMember != null) {
                response.put("status", "success");
                response.put("message", "가입 성공");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "가입 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }


        }
    }
}
