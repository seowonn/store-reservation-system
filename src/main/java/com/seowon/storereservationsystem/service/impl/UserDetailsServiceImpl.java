package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER =
            LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        String[] auth = username.split(" ");
        LOGGER.info(
                "[loadUserByUsername] loadUserByUsername 수행. username : {}",
                username
        );
        if(username.startsWith("USER")){
            return userRepository.findByUserId(auth[1]).orElseThrow(() ->
                    new UsernameNotFoundException(
                            "User not found with username: " + username));
        } else if (username.startsWith("OWNER")) {
            return ownerRepository.findByOwnerId(auth[1]).orElseThrow(() ->
                    new UsernameNotFoundException(
                            "User not found with username: " + username));
        }
        return null;
    }



    private Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
