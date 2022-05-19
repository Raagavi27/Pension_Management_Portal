package com.pensionmanagement.processpension.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPensionInput {

	private Long aadhaarNumber;
	private String name;
	private String dateOfBirth;
	private String pan;
	private BigDecimal salaryEarned;
	private BigDecimal allowances;
	private String typeOfPension;
	private BankDetail bankDetail;
	
	public ProcessPensionInput(Long aadhaarNumber) {
		super();
		this.aadhaarNumber = aadhaarNumber;
	}
}
