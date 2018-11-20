package com.ahmed.anote.login;

public class PasswordChecker {

    private static final String DUMMY_PASSWORD = "password";

    public PasswordChecker() {}

    public boolean isCorrect(String enteredPassword) {
        if (enteredPassword == null) {
            return false;
        }
        else {
            return enteredPassword.equals(DUMMY_PASSWORD);
        }
    }
}
