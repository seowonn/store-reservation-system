package com.seowon.storereservationsystem.entity;

import com.seowon.storereservationsystem.type.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime regAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부를 나타냄, 필요에 따라 로직 변경 가능
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부를 나타냄, 필요에 따라 로직 변경 가능
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보 만료 여부를 나타냄, 필요에 따라 로직 변경 가능
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부를 나타냄, 필요에 따라 로직 변경 가능
    }
}
