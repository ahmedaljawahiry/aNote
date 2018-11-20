package com.ahmed.anote.login;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordCheckerTest {

    private PasswordChecker passwordChecker = new PasswordChecker();
    private static final String DUMMY_PASSWORD = "password";

    @Test
    public void correctPasswordPasses() {
        assertTrue(passwordChecker.isCorrect(DUMMY_PASSWORD));
    }

    @Test
    public void incorrectPasswordFails() {
        assertFalse(passwordChecker.isCorrect("incorrectPW"));
    }

    @Test
    public void nullPasswordFails() {
        assertFalse(passwordChecker.isCorrect(null));
    }
}
