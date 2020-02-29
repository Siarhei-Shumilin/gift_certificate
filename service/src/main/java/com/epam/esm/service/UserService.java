package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.UserDetailsImpl;
import com.epam.esm.util.UserValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public UserService(UserMapper userMapper, UserValidator userValidator) {
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userMapper.findByUserName(username);
        return new UserDetailsImpl(user);
    }

    public long save(User user, Locale locale) {
        if (!userValidator.validateUser(user)){
            throw new GeneralException(ExceptionType.INCORRECT_USER_DATA, locale);
        }
        userMapper.save(user);
        long id = user.getId();
        if (id == 0) {
            throw new GeneralException(ExceptionType.USER_EXISTS_EXCEPTION, locale);
        }
        return id;
    }
}