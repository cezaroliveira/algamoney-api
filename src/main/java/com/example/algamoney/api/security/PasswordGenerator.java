package com.example.algamoney.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordGenerator {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("admin"));
		System.out.println(new BCryptPasswordEncoder().encode("marina"));
	}
	
}
