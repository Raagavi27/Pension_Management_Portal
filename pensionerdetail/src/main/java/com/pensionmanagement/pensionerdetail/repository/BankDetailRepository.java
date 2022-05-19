package com.pensionmanagement.pensionerdetail.repository;

import com.pensionmanagement.pensionerdetail.model.BankDetail;

public interface BankDetailRepository {

	public void loadPensionerDetailsToDatabase();

	public BankDetail getBankDetailsById(Long bankId);

}