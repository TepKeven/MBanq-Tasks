package com.tepkeven.bankservices.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetail extends org.springframework.security.core.userdetails.User {
    
    private Long userId;
    private String firstname;
    private String lastname;

    public CustomUserDetail(Long userId, String firstname, String lastname, String phone, String password, Collection<? extends GrantedAuthority> authorities) {
        super(phone, password, authorities);
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    
}