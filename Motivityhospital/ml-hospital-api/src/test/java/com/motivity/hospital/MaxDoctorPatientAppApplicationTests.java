package com.motivity.hospital;

import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;
import com.motivity.hospital.service.AdminService;
import com.motivity.hospital.service.DoctorService;
import com.motivity.hospital.service.PatientsService;

@SpringBootTest
class MaxDoctorPatientAppApplicationTests {

	@Test
	void contextLoads() {
	}
	
//	@Autowired
//	private AdminService adminService;
//	
//	@MockBean
//	private AdminRepo adminRepo;
//	
//	@Autowired
//	private PatientsService patientsService;
//	
//	@MockBean
//	private PatientRepo patientRepo;
//	
//	@MockBean
//	private DoctorRepo doctorRepo;
//	
//	@Autowired
//	private DoctorService doctorService;
//	
//
//	@Test
//	public void getDoctorsTest() {
//		when(doctorRepo.findAll()).thenReturn((Iterable<Doctors>) Stream.of(new Doctors(4,"hyderabad","Cardiologist", "nikhil.kakkerla@motivitylabs.com", "5", "Nikhil", "male", "Kumar", "Nikhil@123", "8555901493")));
//	}
//	
	

}
