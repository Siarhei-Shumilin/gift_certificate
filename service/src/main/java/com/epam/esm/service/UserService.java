package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.UserExistsException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.UserDetailsImpl;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final MessageSource messageSource;

    public UserService(UserMapper userMapper, MessageSource messageSource) {
        this.userMapper = userMapper;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        return new UserDetailsImpl(user);
    }

    public long save(User user, Locale locale) throws UserExistsException {
        userMapper.save(user);
        long id = user.getId();
        if (id == 0) {
            throw new UserExistsException(messageSource.getMessage("user.exists", null, locale));
        }
        return id;
    }
}