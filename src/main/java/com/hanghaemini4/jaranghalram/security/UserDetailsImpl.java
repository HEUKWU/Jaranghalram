package com.hanghaemini4.jaranghalram.security;

import com.hanghaemini4.jaranghalram.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final String username;

    public UserDetailsImpl(User user, String username) {
        this.user = user;
        this.username = username;
    }

    public User getUser() {
        return user;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        UserRoleEnum role = user.getRole();
//        String authority = role.getAuthority();
//
//        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(simpleGrantedAuthority);
//
//        return authorities;
//    }

    //인터페이스 현재 미사용
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}