package com.pensionmanagement.pensionerdetail.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PensionerDetail {
	@Id
	private Long aadhaarNumber;
	private String name;
	private String dateOfBirth;
	private String pan;
	private BigDecimal salaryEarned;
	private BigDecimal allowances;
	private String typeOfPension;
	@OneToOne(fetch = FetchType.EAGER)
	private BankDetail bankDetail;

}