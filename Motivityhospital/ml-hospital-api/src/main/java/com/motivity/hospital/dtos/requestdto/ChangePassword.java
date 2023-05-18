package com.motivity.hospital.dtos.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private int id;
    private String currentPassword;
    private String newPassword;
}
