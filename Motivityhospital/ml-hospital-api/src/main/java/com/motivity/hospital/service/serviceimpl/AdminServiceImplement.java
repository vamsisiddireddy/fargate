package com.motivity.hospital.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.motivity.hospital.models.Admin;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.service.AdminService;

@Service
public class AdminServiceImplement implements AdminService
{

	
	@Autowired
	private AdminRepo adminRepo;
	
	@Override
	public Admin adminLogin(String emailId, String password) {
		// TODO Auto-generated method stub
		return adminRepo.adminLogin(emailId, password);
	}
	


}
