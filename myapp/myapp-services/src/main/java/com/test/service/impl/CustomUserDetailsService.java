package com.test.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.bean.CustomUserDetails;
import com.test.entity.Users;
import com.test.service.IGenericService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private IGenericService<Users> iGenericService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = iGenericService.find(new Users(), " where username ='"+username+"'");
        Optional<Users> optUsers = Optional.ofNullable(users);
        optUsers.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return optUsers.map(CustomUserDetails::new).get();
    }
}