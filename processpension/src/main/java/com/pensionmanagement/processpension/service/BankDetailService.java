package com.pensionmanagement.processpension.service;

import java.math.BigDecimal;

import com.pensionmanagement.processpension.model.BankDetail;

public interface BankDetailService {
	public BigDecimal bankServiceCharge(BankDetail bankDetail);
}
