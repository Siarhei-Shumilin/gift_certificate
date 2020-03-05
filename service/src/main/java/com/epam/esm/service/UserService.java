package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userMapper.findByUserName(username);
        return new UserDetailsImpl(user);
    }

    public long save(User user) {
        userMapper.save(user);
        long id = user.getId();
        if (id == 0) {
            throw new GeneralException(ExceptionType.USER_EXISTS_EXCEPTION);
        }
        return id;
    }
}