package com.pensionmanagement.processpension.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankDetail {

	private Long id;
	private String bankName;
	private Long accountNumber;
	private String typeOfBank;
}
