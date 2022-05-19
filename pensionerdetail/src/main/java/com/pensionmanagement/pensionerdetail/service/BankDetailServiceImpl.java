package com.pensionmanagement.pensionerdetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pensionmanagement.pensionerdetail.model.BankDetail;
import com.pensionmanagement.pensionerdetail.repository.BankDetailRepository;

@Service
@Transactional
public class BankDetailServiceImpl implements BankDetailService {

	@Autowired
	private BankDetailRepository repository;

	@Override
	public void loadPensionerDetailsToDatabase() {
		repository.loadPensionerDetailsToDatabase();
	}

	@Override
	public BankDetail getBankDetailsById(Long bankId) {
		return repository.getBankDetailsById(bankId);
	}

}
