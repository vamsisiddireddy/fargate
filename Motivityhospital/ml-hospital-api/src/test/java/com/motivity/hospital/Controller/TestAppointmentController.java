package com.motivity.hospital.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motivity.hospital.controller.AppointmentController;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.responsedto.JwtResponseDto;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;
import com.motivity.hospital.models.Appointments;
import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.repository.AppointmentsRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "com.motivity.hospital")
@WebMvcTest(AppointmentController.class)
public class TestAppointmentController {

    @MockBean
    private AppointmentsRepo appointmentsRepo;
    @MockBean
    private AdminRepo adminRepo;
    @MockBean
    private PatientRepo patientRepo;
    @MockBean
    private DoctorRepo doctorRepo;
    @MockBean
    private JavaMailSender javaMailSender;
    @MockBean
    private JWTServiceData jwtServiceData;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private String token;


    public void setup(String email,String password,String role) {
        when(jwtServiceData.loadUserByUsername(email)).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email(email).password(password).authorities(AuthorityUtils.createAuthorityList(role)).build()));
        UserDetails userDetails = jwtServiceData.loadUserByUsername(email);
        token = jwtUtility.generateToken(userDetails);
    }
    @Test
    public void insertAppointmentTest() throws Exception {

        Appointments appointment=new Appointments("101","test"
                ,"985295855","male","23","cardiology");
        //for token creation
        setup("pateint@gmail.com","Test@123","patient");
        when(appointmentsRepo.save(appointment)).thenReturn(appointment);
        mockMvc.perform(MockMvcRequestBuilders.post("/insertAppointment").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(appointment)).header("Authorization", "Bearer " +token)).andExpect(status().isOk()).andReturn();

    }
    @Test
    public void showPendingAppointmentTest() throws Exception {
        setup("doctor@gmail.com","Test@123","doctor");
        Appointments appointment=new Appointments("101","test"
                ,"985295855","male","23","cardiology");
        when(appointmentsRepo.showAndAcceptAppointment("cardiology","pending")).thenReturn(Arrays.asList(appointment,appointment));
        Doctors doctors = new Doctors(101, "test", "testing",
                "doctor", "98745852", "male", "testing department", "doctor@gmail.com", passwordEncoder.encode("Test@123"), "5");
        mockMvc.perform(MockMvcRequestBuilders.post("/showAcceptedAppointments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctors)).header("Authorization", "Bearer " +token)).andExpect(status().isOk()).andReturn();
    }


}
