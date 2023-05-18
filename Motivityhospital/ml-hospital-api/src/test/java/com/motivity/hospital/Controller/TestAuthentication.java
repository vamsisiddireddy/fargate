package com.motivity.hospital.Controller;

import com.motivity.hospital.controller.AdminController;
import com.motivity.hospital.models.Admin;
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
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "com.motivity.hospital")
@WebMvcTest(AdminController.class)
public class TestAuthentication {
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAdminLogin() throws Exception{
        String emailId = "test@gmail.com";
        String password = "Test@123";
        Admin admin = new Admin();
        admin.setEmailId(emailId);
        admin.setPassword(passwordEncoder.encode(password));
        when(adminRepo.findByEmailId(emailId)).thenReturn(admin);
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/adminlogin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"emailId\":\""+emailId+"\",\"password\":\""+password+"\"}"))
                .andExpect(status().isOk()).andReturn();
        mockMvc.perform(post("/adminlogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"emailId\":\""+emailId+"\",\"password\":\""+"password"+"\"}"))
                .andExpect(status().is4xxClientError());
    }

}
