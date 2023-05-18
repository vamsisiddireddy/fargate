package com.motivity.hospital.Controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.responsedto.JwtResponseDto;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;
import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.motivity.hospital.controller.AdminController;
import com.motivity.hospital.repository.AdminRepo;
import com.motivity.hospital.repository.AppointmentsRepo;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.repository.PatientRepo;

import java.util.List;


@ComponentScan(basePackages = "com.motivity.hospital")
@WebMvcTest(AdminController.class)
public class TestAdminController {

	@Autowired
	private MockMvc mockMvc ;
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
	private JWTServiceData jwtServiceData;
	@Autowired
	private JWTUtility jwtUtility;
	@MockBean
	private DoctorService doctorService;
	@Autowired
	private ObjectMapper objectMapper;

	private String token;


	@BeforeEach
	public  void setup() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("admin");
		when(jwtServiceData.loadUserByUsername("admin@gmail.com")).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email("admin@gmail.com").password("Test@123").authorities(authorities).build()));
		UserDetails userDetails = jwtServiceData.loadUserByUsername("admin@gmail.com");
		token = jwtUtility.generateToken(userDetails);
	}

	@Test
	public void doctorRegisterTest() throws Exception {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("admin");
		Mockito.when(jwtServiceData.loadUserByUsername("admin@gmail.com")).thenReturn(new JwtUserDetailsImpl(JwtResponseDto.builder().email("admin@gmail.com").password("Test@123").authorities(authorities).build()));
		Doctors doctorRegisterRequest = new Doctors(101, "test", "testing",
				"doctor", "98745852", "male", "male", "test@gmail.com", "Test@123", "5");
		when(doctorService.saveDoctor(doctorRegisterRequest)).thenReturn(doctorRegisterRequest);
		String doctorObjectJson = objectMapper.writeValueAsString(doctorRegisterRequest);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/doctorRegistration")
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).content(doctorObjectJson);
		MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

}
