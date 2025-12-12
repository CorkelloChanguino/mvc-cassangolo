package com.construction_app.mvc_cassangolo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("cassangolo123!"));
    }
}

