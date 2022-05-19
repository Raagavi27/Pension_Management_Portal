package com.pensionmanagement.pensionerdetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pensionmanagement.pensionerdetail.feign.AuthenticationProxy;
import com.pensionmanagement.pensionerdetail.model.PensionerDetail;
import com.pensionmanagement.pensionerdetail.service.PensionerDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PensionerDetailController {

	@Autowired
	private PensionerDetailService pensionerDetailService;

	@Autowired
	private AuthenticationProxy proxy;

	@GetMapping("PensionerDetailByAadhaar")
	public PensionerDetail pensionerDetailByAadhaar(@RequestHeader("Authorization") String token,
			@RequestParam("aadhaarNumber") Long aadhaarNumber) throws Exception {
		proxy.getValidity(token);
		PensionerDetail pensionerDetails = pensionerDetailService.getPensionerDetails(aadhaarNumber);
		if (pensionerDetails == null) {
			log.warn("Invalid aadhaar number");
			return null;
		} else {
			log.info("Fetching user details using Aadhaar");
			return pensionerDetails;
		}
	}

}
