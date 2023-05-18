package com.motivity.hospital.security;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.*;

import com.motivity.hospital.jwt.filter.JWTFilter;



@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	Logger logger= LoggerFactory.getLogger(SecurityConfig.class);
	@Autowired
	 private JWTFilter jwtFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.csrf().disable();
		http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/swagger-ui/**", "https://ml-hospital-mngmt.azurewebsites.net/**","/v3/api-docs/**","https://ml-hospital-scheduler.azurewebsites.net/").permitAll();
		http.authorizeRequests().antMatchers("/adminlogin","/doctorlogin","/patientlogin","/patientRegister","/api/csrf").permitAll()
				.antMatchers("/patientslist","/doctorRegistration").hasAuthority("admin")
                .antMatchers("/doctorslists").hasAnyAuthority("admin","patient")
				.antMatchers("/updatePatient","/patientPasswordChange","/insertAppointment","/showStatus").hasAuthority("patient")
				.antMatchers("/updateDoctor","/doctorPasswordChange","/acceptAppointments").hasAuthority("doctor")
	   .anyRequest().authenticated();


	}
	private CsrfTokenRepository csrfTokenRepository()
	{
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setSessionAttributeName("_csrf");
		return repository;
	}
		@Bean
	public PasswordEncoder getPasswordEncoder()  {
		return new BCryptPasswordEncoder();
//		return  NoOpPasswordEncoder.getInstance();
	}
	@Bean
	public AuthenticationManager authentications() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
    public ModelMapper getMapper(){
		return  new ModelMapper();
    }
}
