package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.UserDetailsImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserDetailsImpl userDetails;
    @InjectMocks
    private UserService userService;

    @Test(expected = GeneralException.class)
    public void testSaveShouldThrowException() {
        User user = new User();
        userService.save(user);
    }
}