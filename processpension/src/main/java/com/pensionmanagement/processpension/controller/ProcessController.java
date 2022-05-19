package com.pensionmanagement.processpension.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pensionmanagement.processpension.feign.AuthProxy;
import com.pensionmanagement.processpension.feign.PensionerDetailProxy;
import com.pensionmanagement.processpension.model.PensionDetail;
import com.pensionmanagement.processpension.model.ProcessPensionInput;
import com.pensionmanagement.processpension.repository.PensionDetailRepository;
import com.pensionmanagement.processpension.service.BankDetailService;
import com.pensionmanagement.processpension.service.ProcessPensionInputService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:8200")
@Slf4j
public class ProcessController {

	@Autowired
	private PensionerDetailProxy pensionDetailProxy;

	@Autowired
	private ProcessPensionInputService processPensionInputService;

	@Autowired
	private BankDetailService bankDetailService;

	@Autowired
	private AuthProxy authProxy;

	@Autowired
	private PensionDetailRepository outputRepository;

	@PostMapping("ProcessPension")
	public PensionDetail completeProcesing(@RequestHeader("Authorization") String token,
			@RequestBody ProcessPensionInput input) throws Exception {
		authProxy.getValidity(token);
		ProcessPensionInput detail = pensionDetailProxy.pensionerDetailByAadhaar(token, input.getAadhaarNumber());
		if (detail == null) {
			log.warn("Invalid Aadhaar number");
		} else {
			BigDecimal bankServiceCharge = bankDetailService.bankServiceCharge(detail.getBankDetail());
			BigDecimal pensionAmount = processPensionInputService.pensionAmount(detail);
			PensionDetail pensionDetail = new PensionDetail(pensionAmount, bankServiceCharge, input.getAadhaarNumber());
			outputRepository.saveResponse(pensionDetail);
			log.info("Successfully processed the request");
			return pensionDetail;
		}
		return null;
	}

}
