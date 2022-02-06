package com.ssafy.challympic.config.auth;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.UserAuth;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> user.getUser_auth().toString());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUser_pwd();
    }

    @Override
    public String getUsername() {
        return user.getUser_email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 멈춰뒀다면 7일 정지
        return true;
    }
}
