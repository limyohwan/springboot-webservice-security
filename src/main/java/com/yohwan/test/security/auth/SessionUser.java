package com.yohwan.test.security.auth;

import com.yohwan.test.domain.members.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String providerId;

    public SessionUser(Member member) {
        this.name = member.getUsername();
        this.email = member.getEmail();
        this.providerId = member.getProviderId();
    }
}
