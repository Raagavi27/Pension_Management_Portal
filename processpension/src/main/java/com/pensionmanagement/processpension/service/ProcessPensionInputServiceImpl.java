package com.pensionmanagement.processpension.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.pensionmanagement.processpension.model.ProcessPensionInput;

@Service
public class ProcessPensionInputServiceImpl implements ProcessPensionInputService {

	public BigDecimal pensionAmount(ProcessPensionInput pensionerDetailByAadhaar) {
		BigDecimal pensionAmount;
		if (pensionerDetailByAadhaar.getTypeOfPension().equals("Self")) {
			pensionAmount = (pensionerDetailByAadhaar.getSalaryEarned()).multiply(BigDecimal.valueOf(0.8));

		} else {
			pensionAmount = (pensionerDetailByAadhaar.getSalaryEarned()).multiply(BigDecimal.valueOf(0.5));
		}
		return (pensionAmount.add(pensionerDetailByAadhaar.getAllowances())).setScale(2, RoundingMode.HALF_EVEN);
	}

}
