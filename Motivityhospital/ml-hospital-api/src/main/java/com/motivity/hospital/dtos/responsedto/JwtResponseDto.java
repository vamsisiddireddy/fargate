package com.motivity.hospital.dtos.responsedto;

import java.util.Collection;

import javax.persistence.Column;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponseDto {
	private String token;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private int id;
	private String name;
	private String age;
	private String address;
	private String gender;
	private String phoneNo;
	private String department;
	private String lastName;
	private String password;
	private String exp;
}
