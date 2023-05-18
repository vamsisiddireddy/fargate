package com.motivity.hospital.dtos.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptAppointment {
	private int appointmentId;
	private int doctorId;

}
