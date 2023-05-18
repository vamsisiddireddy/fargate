package com.motivity.hospital.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.motivity.hospital.models.Doctors;


public interface DoctorRepo extends CrudRepository<Doctors, Integer>{

	
	@Query("select d from Doctors d where d.email=:email and d.password=:password")
	public Doctors loginDoctor(String email,String password);
	
	@Transactional
    @Modifying
    @Query("update Doctors d set d.firstname=:firstname,d.lastname=:lastname,d.phoneno=:phoneno,d.address=:address,d.exp=:exp where d.id=:id")
    public int updateDoctor(String firstname,String lastname,String phoneno,String address,String exp,int id);
	@Transactional
    @Modifying
    @Query("update Doctors d set d.password=:password where d.id=:id")
    public int changeDoctorPassword(String password,int id);

	public Doctors findByEmail(String username);
}
