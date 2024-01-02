package com.example.carrosapi.api.security;

import com.example.carrosapi.domain.User;
import com.example.carrosapi.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login);
        if(user == null){
            throw new UsernameNotFoundException("Usuario nao encontrado");
        }
        return org.springframework.security.core.userdetails
                .User.withUsername(login).password(user.getSenha()).roles("USER").build();
    }
}
