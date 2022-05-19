package com.pensionmanagement.pensionerdetail.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class BankDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String bankName;
	private Long accountNumber;
	private String typeOfBank;

	public BankDetail(String bankName, Long accountNumber, String typeOfBank) {
		super();
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.typeOfBank = typeOfBank;
	}

}