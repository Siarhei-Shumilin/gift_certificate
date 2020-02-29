package com.epam.esm.util;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class UserValidator {

    public boolean validateUser(User user) {
        Predicate<Integer> isPasswordLengthLess60 = x -> x > 2 && x <= 60;
        Predicate<Integer> isNameLengthLess30 = x -> x > 2 && x < 30;
        String password = user.getPassword();
        String username = user.getUsername();
        boolean testPassword = isPasswordLengthLess60.test(password.length());
        boolean testUserName = isNameLengthLess30.test(username.length());
        return testPassword && testUserName;
    }
}
