package com.motivity.hospital.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motivity.hospital.controller.DoctorController;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.requestdto.ChangePassword;
import com.motivity.hospital.dtos.requestdto.UpdateProfile;
import com.motivity.hospital.dtos.responsedto.JwtResponseDto;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;
import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.repository.AppointmentsRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;
import com.motivity.hospital.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "com.motivity.hospital")
@WebMvcTest(DoctorController.class)
public class TestDoctorController {

    @Mock
    UpdateProfile profile;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdminRepo adminRepo;
    @MockBean
    private PatientRepo patientRepo;
    @MockBean
    private DoctorRepo doctorRepo;
    @MockBean
    private AppointmentsRepo appointmentsRepo;
    @MockBean
    private JavaMailSender javaMailSender;
    @MockBean
    private DoctorService doctorService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JWTServiceData jwtServiceData;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private String token;

    @BeforeEach
    public void setup() {
        when(jwtServiceData.loadUserByUsername("doctor@gmail.com")).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email("doctor@gmail.com").password("Test@123").authorities(AuthorityUtils.createAuthorityList("doctor")).build()));
        UserDetails userDetails = jwtServiceData.loadUserByUsername("doctor@gmail.com");
        token = jwtUtility.generateToken(userDetails);
    }

    @Test
    public void changePasswordTest() throws Exception {
        ChangePassword changePasswordRequest=new ChangePassword(101,"Test@123","Test@321");
        when(doctorRepo.findById(changePasswordRequest.getId())).thenReturn(Optional.of(new Doctors(101, "test", "testing",
                "doctor", "98745852", "male", "testing department", "doctor@gmail.com", passwordEncoder.encode("Test@123"), "5")));
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder=MockMvcRequestBuilders.post("/doctorPasswordChange").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(changePasswordRequest));
        //success case
       mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isAccepted()).andReturn();
       //fail case
       changePasswordRequest.setCurrentPassword("WrongPass@123");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilders=MockMvcRequestBuilders.post("/doctorPasswordChange").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(changePasswordRequest));
        int status = mockMvc.perform(mockHttpServletRequestBuilders).andReturn().getResponse().getStatus();
        Assertions.assertEquals(401,status);
    }

    @Test
    public void updateDoctorTest() throws Exception {
        when(jwtServiceData.loadUserByUsername("doctor@gmail.com")).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email("doctor@gmail.com").password("Test@123").authorities(AuthorityUtils.createAuthorityList("doctor")).build()));
       profile=new UpdateProfile(101,"test","tester","985858555","23","testing","23");
        when(doctorRepo.findById(profile.getId())).thenReturn(Optional.of(new Doctors(101, "test", "testing",
                "doctor", "98745852", "male", "testing department", "doctor@gmail.com", passwordEncoder.encode("Test@123"), "5")));
        when(doctorRepo.updateDoctor(profile.getFirstname(), profile.getLastname(),profile.getPhoneNo(),profile.getAddress(),profile.getExp(), profile.getId())).thenReturn(1);

        //success case
         mockMvc.perform(MockMvcRequestBuilders.post("/updateDoctor").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(this.profile))).andExpect(status().isAccepted()).andReturn();

         profile.setId(201);
         //failed case
        mockMvc.perform(MockMvcRequestBuilders.post("/updateDoctor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.profile))).andExpect(status().isForbidden()).andReturn();

    }


}
