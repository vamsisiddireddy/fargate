package com.motivity.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motivity.hospital.models.Doctors;
@Service
public interface DoctorService {

	Doctors saveDoctor(Doctors doctor);
	
	Doctors loginDoctor(String email,String password);
	List<Doctors> findDoctors();

}
