package com.springboot.session.data.dto;

import com.springboot.session.data.AuthType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberRequestDto {
    private Long id;
    private String name;
    private String userid;
    private String password;
    private String email;
    private AuthType authType;
}
