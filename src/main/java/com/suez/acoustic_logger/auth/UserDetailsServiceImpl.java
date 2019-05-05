package com.suez.acoustic_logger.auth;

import com.suez.acoustic_logger.entity.User;
import com.suez.acoustic_logger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.suez.acoustic_logger.entity.User> optional = userRepository.findById(username);
        User user = optional.get();
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println("pass: " + user.getPassword());
        if(user == null){
            System.out.println("Username: " + username + " not found");
            throw new UsernameNotFoundException("Username: " + username + " not found");
        } else {
            System.out.println("ROLE_" + user.getRole());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_" + user.getRole()));
        }


    }


}