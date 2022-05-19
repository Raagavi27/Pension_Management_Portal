package com.pensionmanagement.processpension.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pensionmanagement.processpension.model.ValidityResponse;

@FeignClient(name = "authorization-ms", url = "${feign.auth.url}")
public interface AuthProxy {

	@GetMapping("/validateToken")
	public ValidityResponse getValidity(@RequestHeader("Authorization") final String token);
}
