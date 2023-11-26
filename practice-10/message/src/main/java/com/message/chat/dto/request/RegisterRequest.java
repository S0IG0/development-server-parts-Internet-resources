package com.message.chat.dto.request;

import lombok.*;

@Getter
@Setter
public class RegisterRequest extends LoginRequest {

    public RegisterRequest(String username, String password) {
        super(username, password);
    }
}


