package com.motivity.hospital.jwt.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.motivity.hospital.exception.UserNotFoundException;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.responsedto.JwtResponseDto;
import com.motivity.hospital.models.Admin;
import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.models.Patients;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;
@Service
@ComponentScan(basePackages = "com.motivity.hospital")
public class JWTServiceData implements UserDetailsService {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private PatientRepo patientRepo;
	@Autowired
	private DoctorRepo doctorRepo;
	private JwtResponseDto jwtResponseDto=new JwtResponseDto();
	
	public JwtUserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin=adminRepo.findByEmailId(username);
		Patients patient=patientRepo.findByEmail(username);
		Doctors doctors=doctorRepo.findByEmail(username);
		
		if(admin!=null) {
			ArrayList<GrantedAuthority> roles=new ArrayList<>();
			roles.add(new SimpleGrantedAuthority("admin"));
			jwtResponseDto.setAuthorities(roles);
			jwtResponseDto.setPhoneNo(admin.getPhoneNo());
			jwtResponseDto.setPassword(admin.getPassword());
			jwtResponseDto.setName(admin.getFirstName());
			jwtResponseDto.setLastName(admin.getLastName());
			jwtResponseDto.setId(admin.getAdminId());
			jwtResponseDto.setEmail(username);
			return new JwtUserDetailsImpl(jwtResponseDto);
			
		}
		else if (patient!=null) {
			ArrayList<GrantedAuthority> role=new ArrayList<>();
			role.add(new SimpleGrantedAuthority("patient"));
			jwtResponseDto.setAuthorities(role);
			jwtResponseDto.setPhoneNo(patient.getPhoneNo());
			jwtResponseDto.setPassword(patient.getPassword());
			jwtResponseDto.setName(patient.getPatientName());
			jwtResponseDto.setId(patient.getPatientId());
			jwtResponseDto.setGender(patient.getPatient_gender());
			jwtResponseDto.setEmail(username);
			jwtResponseDto.setAge(patient.getPatient_age());
			jwtResponseDto.setAddress(patient.getPatientAddress());
			return new JwtUserDetailsImpl(jwtResponseDto);
		}
		else if (doctors!=null) {
			ArrayList<GrantedAuthority> role=new ArrayList<>();
			role.add(new SimpleGrantedAuthority("doctor"));
			jwtResponseDto.setAuthorities(role);
			jwtResponseDto.setPhoneNo(doctors.getPhoneno());
			jwtResponseDto.setPassword(doctors.getPassword());
			jwtResponseDto.setName(doctors.getFirstname());
			jwtResponseDto.setLastName(doctors.getLastname());
			jwtResponseDto.setId(doctors.getId());
			jwtResponseDto.setDepartment(doctors.getDepartment());
			jwtResponseDto.setEmail(username);
			jwtResponseDto.setAddress(doctors.getAddress());
			jwtResponseDto.setExp(doctors.getExp());
			return new JwtUserDetailsImpl(jwtResponseDto);
		}
		else {
			System.out.println("no  data found");
			throw new UserNotFoundException("User does not exist with username "+username);
		}
	} 
}
