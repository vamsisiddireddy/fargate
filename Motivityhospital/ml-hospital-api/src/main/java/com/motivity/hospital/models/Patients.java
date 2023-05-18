package com.motivity.hospital.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

@Entity
@Scope(value = "Prototype")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patients {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int patientId;
	private String patientName;
	private String Patient_age;
	private String patientAddress;
	@Column(unique=true)
	private String email;
	private String patient_gender;
	private String phoneNo;
	private String password;


}
