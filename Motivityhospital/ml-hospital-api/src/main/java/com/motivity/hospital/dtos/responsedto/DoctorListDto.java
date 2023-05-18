package com.motivity.hospital.dtos.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorListDto {
    private int id;
    private String firstname;
    private String lastname;
    private String department;
    private String phoneno;
    private String gender;
    private String email;
    private String exp;
}
