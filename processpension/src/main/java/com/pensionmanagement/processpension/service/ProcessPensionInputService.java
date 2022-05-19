package com.pensionmanagement.processpension.service;

import java.math.BigDecimal;

import com.pensionmanagement.processpension.model.ProcessPensionInput;

public interface ProcessPensionInputService {

	public BigDecimal pensionAmount(ProcessPensionInput pensionerDetailByAadhaar);

}
