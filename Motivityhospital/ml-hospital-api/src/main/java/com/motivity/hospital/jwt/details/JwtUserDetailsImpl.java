package com.motivity.hospital.jwt.details;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.motivity.hospital.dtos.responsedto.JwtResponseDto;

public class JwtUserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private  JwtResponseDto jwtResponseDto;
	 
	public JwtUserDetailsImpl(JwtResponseDto jwtResponseDto) {
		super();
		this.jwtResponseDto = jwtResponseDto;
	}


	public Collection<? extends GrantedAuthority> getAuthorities() {

		return jwtResponseDto.getAuthorities();

	}

	
	public String getPassword() {
		return jwtResponseDto.getPassword();
	}


	public String getUsername() {
	
		return jwtResponseDto.getEmail();
	}

	
	public boolean isAccountNonExpired() {
		
		return true;
	}


	public boolean isAccountNonLocked() {
		
		return true;
	}

	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	public boolean isEnabled() {
		
		return true;
	}
	public String getPhoneNo() {
		return jwtResponseDto.getPhoneNo();
	}
	
	public String getFirstName() {
		return jwtResponseDto.getName();
	}
	
	public String getLastName() {
		return jwtResponseDto.getLastName();
	}
	public int getId() {
		return jwtResponseDto.getId();
	}
	public String getDepartment() {
		return jwtResponseDto.getDepartment();
	}
	public String getGender(){
		return jwtResponseDto.getGender();
	}
	public  String getAddress(){
		return jwtResponseDto.getAddress();
	}
	public String getAge(){
		return jwtResponseDto.getAge();
	}
	public  String getExp(){
		return jwtResponseDto.getExp();
	}
}
