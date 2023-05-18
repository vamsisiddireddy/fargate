package com.motivity.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.motivity.hospital.models.Patients;
@Service
public interface PatientsService {

	Patients savePatients(Patients patients);
	
	Patients loginPatient(String email,String password);
	List<Patients> findpatients();

}
