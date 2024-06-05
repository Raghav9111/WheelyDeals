package com.wheelyDeals.model;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationDateModel {
	private LocalDate fromDate;
	private LocalDate toDate;
}
