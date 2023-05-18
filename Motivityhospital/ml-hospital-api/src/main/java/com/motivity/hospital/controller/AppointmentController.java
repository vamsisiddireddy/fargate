package com.motivity.hospital.controller;

import java.util.List;

import com.motivity.hospital.dtos.requestdto.AcceptAppointment;
import com.motivity.hospital.dtos.requestdto.ConsultantBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.motivity.hospital.models.Appointments;
import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.models.Patients;
import com.motivity.hospital.repository.AppointmentsRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;
import com.motivity.hospital.service.EmailNotificationService;


@RestController
public class AppointmentController {

	@Autowired
	private PatientRepo patientRepo;

	@Autowired
	private EmailNotificationService emailNotificationService;
	@Autowired
	private AppointmentsRepo appointmentsRepo;
	@Autowired
	private DoctorRepo doctorRepo;
	@Autowired
	private AppointmentsRepo appointments;

	@PostMapping("/insertAppointment")
	public String insertAppointment(@RequestBody ConsultantBooking appointments) {

		String id=Integer.toString(appointments.getPatientId());
		Appointments appointments1=new Appointments(id,appointments.getPatientName()
				,appointments.getPatientPhoneNo(),appointments.getPatientGender(),appointments.getPatientAge(),appointments.getProblem()
		);

//		Patients patient = patientRepo.findById(Integer.parseInt(appointments.getPatientId())).get();
		Appointments appointmentData = appointmentsRepo.save(appointments1);
		if(appointmentData==null)
		{
			return "failed";
		}
		else
		{
			String text = "your request for doctor appointment was created.\n"
					+ "  we will notify you whenever doctor was accepted";
			String to = appointments.getPatientemail();
			String subject = "doctor appointment request created";
			emailNotificationService.sendEmail(to, subject, text);

			return "success";
		}
	}

	@PostMapping("/acceptAppointments" )
	public List<Appointments> acceptAppointments(@RequestBody AcceptAppointment acceptAppointment) {
		Doctors doctor = doctorRepo.findById(acceptAppointment.getDoctorId()).get();
		Appointments appointment = appointmentsRepo.findById(acceptAppointment.getAppointmentId()).get();
		Patients patient = patientRepo.findById(Integer.parseInt(appointment.getPatientId())).get();
		int success=appointmentsRepo.acceptUpdate("", "", doctor.getFirstname()+" "+doctor.getLastname(), doctor.getGender(),acceptAppointment.getAppointmentId(),"accepted",doctor.getPhoneno(),doctor.getId());
		List<Appointments> appList = appointmentsRepo.showAndAcceptAppointment(doctor.getDepartment(), "pending");
		if(success == 1) {
			String text = "<h1>Hai"+patient.getPatientName()+"</h1>"
					+ "<br><i>your appointment was accepted by "+ doctor.getFirstname()+"</i>,"
					+ "<br> <a href='#'> if any queries contact us </a>"
					+"<br> <br>"
					+"<h4>Thank you.</h4>"
					+"<br><p> Motivity hospitals<p>";
			String to = patient.getEmail();
			String subject = "motivity hospital appointment status";
			emailNotificationService.sendEmail(to, subject, text);
		}
		return appList;
	}
	@PostMapping("showPendingAppointments")
	public List<Appointments> showPendingAppointments(@RequestBody Doctors doctor){

		List<Appointments> appList = appointmentsRepo.showAndAcceptAppointment(doctor.getDepartment(), "pending");
		return appList;
	}
	@PostMapping("showAcceptedAppointments")
	public  List<Appointments> showAcceptedAppointments(@RequestBody Doctors doctor){
		List<Appointments> appList = appointmentsRepo.showAcceptedAppointment(doctor.getDepartment(),doctor.getId(),"accepted");
		return appList;
	}

	@GetMapping("/showStatus")
	public List<Appointments> showStatus(@RequestParam("id") int patientId)
	{
		List<Appointments> pt = appointmentsRepo.patientsidestatus(Integer.toString(patientId));
		return pt;
	}


}
