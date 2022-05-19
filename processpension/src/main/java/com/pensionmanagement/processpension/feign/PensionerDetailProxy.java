package com.pensionmanagement.processpension.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.pensionmanagement.processpension.model.ProcessPensionInput;

@FeignClient(name = "pensioner-detail", url = "${feign.pension.url}")
public interface PensionerDetailProxy {

	@GetMapping("PensionerDetailByAadhaar")
	public ProcessPensionInput pensionerDetailByAadhaar(@RequestHeader("Authorization") String token,
			@RequestParam("aadhaarNumber") Long aadhaarNumber);
}
