package com.epam.esm.util;

import com.epam.esm.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    @Test
    public void testValidateUserShouldReturnTrue() {
        UserValidator userValidator = new UserValidator();
        User user = new User();
        user.setUsername("sadfsdf");
        user.setPassword("asd");
        Assert.assertTrue(userValidator.validateUser(user));
    }

    @Test
    public void testValidateUserShouldReturnFalse() {
        UserValidator userValidator = new UserValidator();
        User user = new User();
        user.setUsername("sadfsdf");
        user.setPassword("asd,./");
        Assert.assertFalse(userValidator.validateUser(user));
    }
}