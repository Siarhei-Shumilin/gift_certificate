package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.UserExistsException;
import com.epam.esm.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private UserService userService;

    @Test(expected = UserExistsException.class)
    public void testSaveShouldThrowException() {
        User user = new User();
        userService.save(user, new Locale("en"));
    }
}