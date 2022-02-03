package com.studentapp.studentApp.exception;

public class StudentNotFoundException extends RuntimeException {

    private String message;

    public StudentNotFoundException(String username) {
        this.message = "Studenta o loginie " + username + " nie znaleziono w systemie.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
