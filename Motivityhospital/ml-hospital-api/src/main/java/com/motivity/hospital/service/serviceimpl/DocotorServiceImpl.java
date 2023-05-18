package com.motivity.hospital.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.service.DoctorService;
@Service
public class DocotorServiceImpl implements DoctorService{

	@Autowired
	private DoctorRepo doctorRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Doctors loginDoctor(String email, String password) {
		
		return doctorRepo.loginDoctor(email, password);
	}
	@Override
	public Doctors saveDoctor(Doctors doctor) {
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		return doctorRepo.save(doctor);
	}
	 @Override
		public List<Doctors> findDoctors() {
			// TODO Auto-generated method stub
			return (List<Doctors>) doctorRepo.findAll();
		}

}
