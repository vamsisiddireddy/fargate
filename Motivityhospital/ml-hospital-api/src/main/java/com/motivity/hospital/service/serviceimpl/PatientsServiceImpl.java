package com.motivity.hospital.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.motivity.hospital.models.Patients;
import com.motivity.hospital.repository.PatientRepo;


@Service
public class PatientsServiceImpl implements com.motivity.hospital.service.PatientsService{

	@Autowired
	private PatientRepo patientRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Patients savePatients(Patients patients) {
		patients.setPassword(passwordEncoder.encode(patients.getPassword()));
		return patientRepo.save(patients);
	}

	@Override
	public Patients loginPatient(String email, String password) {
	
		return patientRepo.loginPatient(email, password);
	}
	@Override
	public List<Patients> findpatients() {
		// TODO Auto-generated method stub
		return (List<Patients>) patientRepo.findAll();
	}

}
