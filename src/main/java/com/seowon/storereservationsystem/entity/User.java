package com.seowon.storereservationsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seowon.storereservationsystem.type.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    @JsonManagedReference
    private List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    @JsonManagedReference
    private List<Review> resviewList = new ArrayList<>();

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
