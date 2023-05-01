package com.ttapractice.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttapractice.payload.pojos.Auth;

public class AuthModule {

    public String createAuthLoad() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");

        String AUTH = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(auth);

        return AUTH;

    }
}
