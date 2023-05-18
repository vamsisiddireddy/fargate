package com.motivity.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.motivity.hospital.models.Appointments;


@Service
public interface AppointmentService  {


	public List<Appointments> patientSideStatus(String patientId);
	
	Appointments saveAppointments(Appointments appointments);

    public List<Appointments> showAndAcceptAppointment(String department, String status);
	public int acceptUpdate(String time,String date,String doctorName,String doctorGender,int appointmentId,String status,String doctorPhoneNo);
	
}
