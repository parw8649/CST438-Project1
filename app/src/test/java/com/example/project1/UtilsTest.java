package com.example.project1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.project1.Utility.Utils;
import com.example.project1.model.User;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void checkUsername() {

        User user = new User("User1", "User123");

        boolean isValid = Utils.verifyUsername("User1", user.getUsername());

        assertTrue(isValid);

        user.setUsername("User2");

        isValid = Utils.verifyUsername("User1", user.getUsername());

        assertFalse(isValid);
    }

    @Test
    public void checkPassword() {

        User user = new User("User1", "User123");

        boolean isValid = Utils.verifyPassword("User123", user.getPassword());

        assertTrue(isValid);

        user.setPassword("User2");

        isValid = Utils.verifyPassword("User123", user.getPassword());

        assertFalse(isValid);
    }
}
