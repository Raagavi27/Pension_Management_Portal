package com.pensionmanagement.processpension.model;

import java.math.BigDecimal;

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
public class PensionDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal pensionAmount;
	private BigDecimal bankServiceCharge;
	private Long aadhaarNumber;

	public PensionDetail(BigDecimal pensionAmount, BigDecimal bankServiceCharge, Long aadhaarNumber) {
		super();
		this.pensionAmount = pensionAmount;
		this.bankServiceCharge = bankServiceCharge;
		this.aadhaarNumber = aadhaarNumber;
	}

}
