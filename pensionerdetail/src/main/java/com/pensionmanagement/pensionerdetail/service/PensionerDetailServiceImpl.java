package com.pensionmanagement.pensionerdetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pensionmanagement.pensionerdetail.model.PensionerDetail;
import com.pensionmanagement.pensionerdetail.repository.PensionerDetailRepository;

@Service
@Transactional
public class PensionerDetailServiceImpl implements PensionerDetailService {

	@Autowired
	private PensionerDetailRepository repository;

	@Override
	public void loadPensionerDetails() {
		repository.loadPensionerDetails();
	}

	@Override
	public PensionerDetail getPensionerDetails(Long aadhaarNumber) {
		return repository.getPensionerDetails(aadhaarNumber);
	}

}
