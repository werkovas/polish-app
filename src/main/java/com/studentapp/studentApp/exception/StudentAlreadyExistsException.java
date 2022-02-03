package com.studentapp.studentApp.exception;

public class StudentAlreadyExistsException extends RuntimeException{

    private String username;

    public StudentAlreadyExistsException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Student o podanym loginie : " + username + " już istnieje w systemie.";
    }
}
