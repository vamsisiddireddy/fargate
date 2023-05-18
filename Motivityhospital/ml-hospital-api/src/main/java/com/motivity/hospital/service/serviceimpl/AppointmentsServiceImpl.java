package com.motivity.hospital.service.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motivity.hospital.models.Appointments;
import com.motivity.hospital.repository.AppointmentsRepo;
import com.motivity.hospital.service.AppointmentService;

@Service
public class AppointmentsServiceImpl implements AppointmentService {

	private AppointmentsRepo appointmentsRepo;
	@Override
	public Appointments saveAppointments(Appointments appointments) {
		
		return appointmentsRepo.save(appointments);
	}
	@Override
	public List<Appointments> patientSideStatus(String patientId) {
		
		return appointmentsRepo.patientsidestatus(patientId);
	}
	@Override
	public List<Appointments> showAndAcceptAppointment(String department, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int acceptUpdate(String time, String date, String doctorName, String doctorGender, int appointmentId,
			String status, String doctorPhoneNo) {
		
		return 0;
	}
	

}
