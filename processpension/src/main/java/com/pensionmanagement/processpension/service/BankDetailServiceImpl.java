package com.pensionmanagement.processpension.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.pensionmanagement.processpension.model.BankDetail;

@Service
public class BankDetailServiceImpl implements BankDetailService {
	public BigDecimal bankServiceCharge(BankDetail bankDetail) {
		BigDecimal bankServiceCharge;

		if (bankDetail.getTypeOfBank().equals("Private")) {
			bankServiceCharge = BigDecimal.valueOf(550);
		} else {
			bankServiceCharge = BigDecimal.valueOf(500);
		}
		return (bankServiceCharge).setScale(2, RoundingMode.HALF_EVEN);
	}
}
