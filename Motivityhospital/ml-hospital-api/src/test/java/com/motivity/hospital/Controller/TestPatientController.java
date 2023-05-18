package com.motivity.hospital.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motivity.hospital.controller.PatientController;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.requestdto.ChangePassword;
import com.motivity.hospital.dtos.requestdto.UpdateProfile;
import com.motivity.hospital.dtos.responsedto.JwtResponseDto;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;
import com.motivity.hospital.models.Patients;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.repository.AppointmentsRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;
import com.motivity.hospital.service.PatientsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "com.motivity.hospital")
@WebMvcTest(PatientController.class)
public class TestPatientController {
    @Autowired
    private MockMvc mockMvc ;
    @MockBean
    private AdminRepo adminRepo;
    @MockBean
    private PatientRepo patientRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @MockBean
    UpdateProfile profile;
    @Autowired
    JWTUtility jwtUtility;
    @MockBean
    private DoctorRepo doctorRepo;
    @MockBean
    private AppointmentsRepo appointmentsRepo;
    @MockBean
    private JavaMailSender javaMailSender;
    @MockBean
    private JWTServiceData jwtServiceData;
    @MockBean
    private PatientsService patientsService;
    @Autowired
    private ObjectMapper objectMapper;
    private String token;
    @BeforeEach
    public void setup() {
        when(jwtServiceData.loadUserByUsername("patient@gmail.com")).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email("patient@gmail.com").password("Test@123").authorities(AuthorityUtils.createAuthorityList("patient")).build()));
        UserDetails userDetails = jwtServiceData.loadUserByUsername("patient@gmail.com");
        token = jwtUtility.generateToken(userDetails);
    }
    @Test
    public void patientRegisterTest() throws Exception {

        when(patientsService.savePatients(new Patients())).thenReturn(new Patients(101,"test", "20",
                "testing", "test@gmail.com", "male", "9852525552", "test@gmail.com"));
        Patients patientRegisterRequest = new Patients(0,"test", "20",
                "testing", "test@gmail.com", "male", "9852525552", "test@gmail.com");
        String doctorObjectJson = objectMapper.writeValueAsString(patientRegisterRequest);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/patientRegister")
                .contentType(MediaType.APPLICATION_JSON).content(doctorObjectJson);
        MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }
    @Test
    public void changePasswordTest() throws Exception {
        ChangePassword changePasswordRequest=new ChangePassword(101,"Test@123","Test@321");

        when(patientRepo.findById(changePasswordRequest.getId())).thenReturn(Optional.of(new Patients(101, "test", "23",
                 "testing department", "patient@gmail.com","male","98529525", passwordEncoder.encode("Test@123"))));

        //success case
        mockMvc.perform(MockMvcRequestBuilders.post("/patientPasswordChange").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(changePasswordRequest))).andExpect(status().isAccepted()).andReturn();
        //fail case
        changePasswordRequest.setCurrentPassword("WrongPass@123");

        int status = mockMvc.perform(MockMvcRequestBuilders.post("/patientPasswordChange").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(changePasswordRequest))).andReturn().getResponse().getStatus();
        Assertions.assertEquals(401,status);
    }
    @Test
    public void updatePatientTest() throws Exception {
        when(jwtServiceData.loadUserByUsername("patient@gmail.com")).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email("patient@gmail.com").password("Test@123").authorities(AuthorityUtils.createAuthorityList("patient")).build()));
        profile=new UpdateProfile(101,"test","tester","985858555","23","testing","23");
        when(patientRepo.findById(profile.getId())).thenReturn(Optional.of(new Patients(101, "test", "23",
                "testing department", "patient@gmail.com","male","98529525", "5")));
        when(patientRepo.updatePatient(profile.getFirstname(), profile.getPhoneNo(),profile.getAge(),profile.getAddress(), profile.getId())).thenReturn(1);

        //success case
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePatient").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(profile))).andExpect(status().isAccepted()).andReturn();

        profile.setId(201);
        //failed case
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePatient").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.profile))).andExpect(status().isForbidden()).andReturn();

    }
}
