package org.example.privateclinicwebsitespringboot.DTO;

import lombok.Data;

@Data
public class SignInDTO {
    private String username;
    private String password;

    public SignInDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
