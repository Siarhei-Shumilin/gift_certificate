package com.epam.esm.util;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private static final String PATTERN_NAME = "[A-Za-z0-9]{1,30}";
    private static final String PATTERN_PASS = "[A-Za-z0-9]{1,60}";

    public boolean validateUser(User user) {
      return user.getUsername().matches(PATTERN_NAME)  && user.getPassword().matches(PATTERN_PASS);
    }
}
