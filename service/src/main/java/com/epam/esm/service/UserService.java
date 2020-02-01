package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.UserExistsException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        return new MyUserDetails(user);
    }

    public String save(User user) throws UserExistsException {
        userMapper.save(user);
        long id = user.getId();
        if (id == 0) {
            throw new UserExistsException("User exists");
        }
        return "Saved user with id = " + id;
    }
}