package com.motivity.hospital.service;

import org.springframework.stereotype.Service;

import com.motivity.hospital.models.Admin;

@Service
public interface AdminService {
	
	public Admin adminLogin(String emailId,String password);

}
