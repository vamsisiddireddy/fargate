package com.motivity.hospital.dtos.responsedto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientLoginResponse{
	private String token;
	private String emailId;
	private Collection<? extends GrantedAuthority> authorities;
	private String firstName;
	private int id;
	private  String phoneNo;
	private String gender;
	private String address;
	private String age;
}
