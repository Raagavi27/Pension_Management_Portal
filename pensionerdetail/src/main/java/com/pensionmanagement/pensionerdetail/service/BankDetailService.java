package com.pensionmanagement.pensionerdetail.service;

import com.pensionmanagement.pensionerdetail.model.BankDetail;

public interface BankDetailService {

	public void loadPensionerDetailsToDatabase();

	public BankDetail getBankDetailsById(Long bankId);

}
