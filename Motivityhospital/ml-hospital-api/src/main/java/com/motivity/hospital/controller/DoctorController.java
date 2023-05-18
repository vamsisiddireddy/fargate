package com.motivity.hospital.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.motivity.hospital.dtos.requestdto.ChangePassword;
import com.motivity.hospital.dtos.requestdto.UpdateProfile;
import com.motivity.hospital.dtos.responsedto.DoctorListDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.motivity.hospital.exception.PasswordMisMatchException;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.requestdto.DoctorLogin;
import com.motivity.hospital.dtos.responsedto.DoctorLoginResponse;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;
import com.motivity.hospital.models.Doctors;
import com.motivity.hospital.repository.DoctorRepo;
import com.motivity.hospital.service.DoctorService;


@RestController
public class DoctorController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private DoctorRepo doctorRepo;
	@Autowired
	private JWTServiceData adminServiceData;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private JWTUtility jwtUtility;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private ModelMapper modelMapper;
	@PostMapping("doctorRegistration")
    public String registerDoctor(@RequestBody Doctors doctor) {
        Doctors doctors = doctorService.saveDoctor(doctor);
        
        if (doctors == null)
            return "failed";
        else
           return "success";
    }
	@PostMapping("/doctorlogin")
     public	ResponseEntity<DoctorLoginResponse> loginDoctor(@RequestBody DoctorLogin doctorLogin) throws Exception {
			long start=System.currentTimeMillis();
			Logger logger=LoggerFactory.getLogger(DoctorController.class);
			try {
				Authentication authenticate = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken
								(doctorLogin.getEmail(), doctorLogin.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authenticate);
			}
			catch (BadCredentialsException e) {
				throw new Exception("INVALID_CREDENTIALS",e);
			}
			logger.info(doctorLogin.getEmail());
			JwtUserDetailsImpl userDetails=adminServiceData.loadUserByUsername(doctorLogin.getEmail());
			logger.info(userDetails.getUsername());
			if(passwordEncoder.matches(doctorLogin.getPassword(), userDetails.getPassword())) {
				final String token=jwtUtility.generateToken(userDetails);
				logger.info(token);
				long end =System.currentTimeMillis();
				logger.info("completed times of the process"+ (end-start));
				DoctorLoginResponse doctorLoginResponse = new DoctorLoginResponse(token, userDetails.getUsername(),
						userDetails.getAuthorities(),
						userDetails.getFirstName(), userDetails.getLastName(), userDetails.getDepartment(), userDetails.getId()
						, userDetails.getPhoneNo(), userDetails.getAddress(), userDetails.getExp());
			return new ResponseEntity<>(doctorLoginResponse,HttpStatus.OK);
			}
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	@PostMapping("/doctorslists")
	public ResponseEntity<List<DoctorListDto>> doctorsList()
	{
		List<Doctors> doctorsList=doctorService.findDoctors();
		List<DoctorListDto> list=doctorsList.stream().map(doctor ->modelMapper.map(doctor, DoctorListDto.class)).collect(Collectors.toList());
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("updateDoctor")
    public ResponseEntity<Doctors> updateDoctors(@RequestBody UpdateProfile profile)
    {

        int result=doctorRepo.updateDoctor(profile.getFirstname(), profile.getLastname(),profile.getPhoneNo(),profile.getAddress(),profile.getExp(), profile.getId());
                
        if(result==1)
        {
            Doctors doctors= doctorRepo.findById(profile.getId()).get();
			return new ResponseEntity<>(doctors,HttpStatus.ACCEPTED);
        }
        else {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        
    }
	@PostMapping("/doctorPasswordChange")
    public ResponseEntity<String> ChangePassword(@RequestBody ChangePassword changePassword) {
		Doctors doctor = doctorRepo.findById(changePassword.getId()).get();
		if (passwordEncoder.matches(changePassword.getCurrentPassword(),doctor.getPassword())) {
			int result = doctorRepo.changeDoctorPassword(passwordEncoder.encode(changePassword.getNewPassword()), changePassword.getId());
			return new ResponseEntity<>("password changed successfully..",HttpStatus.ACCEPTED);
		}
		else {
			throw new PasswordMisMatchException("old password mismatch please enter correct password....");
		}


	}
}
