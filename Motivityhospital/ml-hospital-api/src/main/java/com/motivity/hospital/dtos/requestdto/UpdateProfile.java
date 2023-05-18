package com.motivity.hospital.dtos.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfile {
    private int id;
    private String firstname;
    private String lastname;
    private String phoneNo;
    private String age;
    private String address;
    private String exp;
}
