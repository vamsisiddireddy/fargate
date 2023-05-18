package com.motivity.hospital.dtos.responsedto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginResponse {

	private String token;
	private String emailId;
	private Collection<? extends GrantedAuthority> authorities;
	private String firstName;
	private String lastName;
	private int id;
//	private String csrf_Token;
}

