package com.motivity.hospital.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.motivity.hospital.models.Patients;



public interface PatientRepo extends CrudRepository<Patients, Integer>{

	@Query("select p from Patients p where p.email=:email and p.password=:password")
	public Patients loginPatient(String email,String password);
	
	 @Transactional
	    @Modifying
	    @Query("update Patients p set p.patientName=:patientName, p.phoneNo=:phoneNo,p.Patient_age=:Patient_age,p.patientAddress=:patientAddress where p.patientId=:patientId")
	    public int updatePatient(String patientName,String phoneNo,String Patient_age,String patientAddress,int patientId);
	 @Transactional
	    @Modifying
	    @Query("update Patients p set p.password=:password where p.patientId=:patientId")
	    public int changePatientPassword(String password,int patientId);

	public Patients findByEmail(String username);
}
