package com.motivity.hospital.dtos.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultantBooking {
    private int patientId;
    private String patientName;
    private String patientPhoneNo;
    private String patientGender;
    private String patientAge;
    private  String patientemail;
    private String problem;
}
