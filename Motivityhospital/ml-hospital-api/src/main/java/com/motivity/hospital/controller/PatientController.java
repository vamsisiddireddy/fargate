package com.motivity.hospital.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.motivity.hospital.dtos.requestdto.ChangePassword;
import com.motivity.hospital.dtos.requestdto.UpdateProfile;
import com.motivity.hospital.dtos.responsedto.PatientListDto;
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

import com.motivity.hospital.exception.NoIdPresentsException;
import com.motivity.hospital.exception.PasswordMisMatchException;
import com.motivity.hospital.jwt.details.JwtUserDetailsImpl;
import com.motivity.hospital.dtos.requestdto.PatientLogin;
import com.motivity.hospital.dtos.responsedto.PatientLoginResponse;
import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;
import com.motivity.hospital.models.Patients;
import com.motivity.hospital.repository.PatientRepo;
import com.motivity.hospital.service.PatientsService;


@RestController
public class PatientController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTServiceData adminServiceData;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private JWTUtility jwtUtility;
	@Autowired
	private PatientsService patientsService;
	@Autowired
	private PatientRepo patientRepo;
	@Autowired
	private ModelMapper modelMapper;
	@PostMapping("/patientRegister")
    public String patientRegister(@RequestBody Patients patients)
    {
        Patients patient = patientsService.savePatients(patients);
        if(patient==null)
        {
            return "failed";
        }
        else
        {
            return "success";
        }
    }
	@PostMapping("/patientlogin")
    public ResponseEntity<PatientLoginResponse> patientLogin(@RequestBody PatientLogin patientLogin) throws Exception{
		long start=System.currentTimeMillis();
		Logger logger=LoggerFactory.getLogger(PatientController.class);
		try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken
							(patientLogin.getEmail(), patientLogin.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
		}
		catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS",e);
		}
		logger.info(patientLogin.getEmail());
		JwtUserDetailsImpl userDetails=adminServiceData.loadUserByUsername(patientLogin.getEmail());
		logger.info(userDetails.getUsername());
		if(passwordEncoder.matches(patientLogin.getPassword(), userDetails.getPassword())) {
			final String token=jwtUtility.generateToken(userDetails);
			logger.info(token);
			long end =System.currentTimeMillis();
			logger.info("completed times of the process"+ (end-start));
			PatientLoginResponse patientLoginResponse = new PatientLoginResponse(token, userDetails.getUsername(),
					userDetails.getAuthorities(),
					userDetails.getFirstName(), userDetails.getId(), userDetails.getPhoneNo(), userDetails.getGender()
					, userDetails.getAddress(), userDetails.getAge());
		return new ResponseEntity<>(patientLoginResponse,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
	@GetMapping("/patientslist")
	public ResponseEntity<List<PatientListDto> > getPatients()
	{
		 List<Patients> patients= patientsService.findpatients();
		 List<PatientListDto> list= patients.stream().map(patient->modelMapper.map(patient, PatientListDto.class)).collect(Collectors.toList());
         return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("updatePatient")
    public ResponseEntity<Patients> updatePatient(@RequestBody UpdateProfile profile)
    {

        int result=patientRepo.updatePatient(profile.getFirstname(), profile.getPhoneNo(), profile.getAge(),profile.getAddress(), profile.getId());

		if(result==1)
        {
			Patients patients= patientRepo.findById(profile.getId()).get();
            return new ResponseEntity<>(patients,HttpStatus.ACCEPTED) ;
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

	@PostMapping("/patientPasswordChange")
    public ResponseEntity<String> ChangePassword(@RequestBody ChangePassword changePassword)
    {
		if(changePassword==null)
		{
			throw new NoIdPresentsException("patient "+changePassword.getId()+" is does not exists");
		}
		
		Patients patient= patientRepo.findById(changePassword.getId()).get();
		
		
		if (passwordEncoder.matches(changePassword.getCurrentPassword(),patient.getPassword())) {
			int result = patientRepo.changePatientPassword(passwordEncoder.encode(changePassword.getNewPassword()), changePassword.getId());
			return new ResponseEntity<>("password changed successfully..", HttpStatus.ACCEPTED);
		}else {
				throw new PasswordMisMatchException("password mismatch please enter correct password..");
        }
    }
}
