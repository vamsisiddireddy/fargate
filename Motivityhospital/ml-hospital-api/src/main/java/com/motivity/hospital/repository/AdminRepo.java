package com.motivity.hospital.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.motivity.hospital.models.Admin;

public interface AdminRepo extends CrudRepository<Admin, Integer>
{
	
	@Query("select a from Admin a where a.emailId = :emailId and a.password = :password")
	public Admin adminLogin(String emailId,String password);

	public Admin findByEmailId(String username);

}
