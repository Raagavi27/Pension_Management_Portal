package com.pensionmanagement.pensionerdetail.repository;

import com.pensionmanagement.pensionerdetail.model.PensionerDetail;

public interface PensionerDetailRepository {

	public void loadPensionerDetails();

	public PensionerDetail getPensionerDetails(Long aadhaarNumber);
}
