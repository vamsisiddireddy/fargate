package com.motivity.hospital.controller;


import com.motivity.hospital.models.Admin;
import com.motivity.hospital.repository.AdminRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.requestdto.AdminLogin;
import com.motivity.hospital.dtos.responsedto.AdminLoginResponse;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AdminController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTServiceData adminServiceData;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private JWTUtility jwtUtility;
	@Autowired
	private AdminRepo adminRepo;


	@PostMapping("/adminlogin")
	public AdminLoginResponse login(@RequestBody AdminLogin adminlogin, HttpServletRequest request, HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();

		Logger logger = LoggerFactory.getLogger(AdminController.class);
		try {

			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken
							(adminlogin.getEmailId(), adminlogin.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
		}
		catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS",e);
		}
		logger.info(adminlogin.getEmailId());

		JwtUserDetailsImpl userDetails = adminServiceData.loadUserByUsername(adminlogin.getEmailId());
		if (passwordEncoder.matches(adminlogin.getPassword(), userDetails.getPassword())) {
			final String token = jwtUtility.generateToken(userDetails);
			logger.info(token);
			long end = System.currentTimeMillis();
			logger.info("completed times of the process" + (end - start));

			return new AdminLoginResponse(token, userDetails.getUsername(),
					userDetails.getAuthorities(),
					userDetails.getFirstName(), userDetails.getLastName(), userDetails.getId());
		}
		return null;
	}
	@GetMapping("sample")
	public String getSample(){
		return "sandeep";
	}
}
