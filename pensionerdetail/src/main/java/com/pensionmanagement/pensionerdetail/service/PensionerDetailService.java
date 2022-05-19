package com.pensionmanagement.pensionerdetail.service;

import com.pensionmanagement.pensionerdetail.model.PensionerDetail;

public interface PensionerDetailService {
	public void loadPensionerDetails();

	public PensionerDetail getPensionerDetails(Long aadhaarNumber);
}
