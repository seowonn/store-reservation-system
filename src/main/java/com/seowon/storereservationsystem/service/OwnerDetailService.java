package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OwnerDetailService implements UserDetailsService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Owner owner = ownerRepository.findByOwnerId(username).orElseThrow(() ->
                new UsernameNotFoundException("Owner not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                owner.getOwnerId(), owner.getPassword(), getAuthorities(owner));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Owner owner) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        return authorities;
    }
}
