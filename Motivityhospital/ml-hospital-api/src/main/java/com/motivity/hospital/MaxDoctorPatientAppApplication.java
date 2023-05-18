package com.motivity.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@ComponentScan(value = "com.motivity.hospital",useDefaultFilters = false)
@SpringBootApplication
public class MaxDoctorPatientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxDoctorPatientAppApplication.class, args);
	}
//	@Bean
//public WebMvcConfigurer configure() {
//	return new WebMvcConfigurer() {
//
//		@Override
//		public void addCorsMappings(CorsRegistry registry) {
//			registry.addMapping("/http://localhost:3000/").allowedOrigins("/*");
//		}
//		
//	};
//}
}
