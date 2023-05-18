package com.motivity.hospital.dtos.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientListDto {
    private int patientId;
    private String patientName;
    private String patientAddress;
    private String email;
    private String phoneNo;
    private String patient_gender;
}
