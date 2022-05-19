package com.pensionmanagement.pensionerdetail.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pensionmanagement.pensionerdetail.model.ValidityResponse;

@FeignClient(name = "authorization", url = "${feign_auth_url}")
@Component
public interface AuthenticationProxy {
	@GetMapping("/validateToken")
	public ValidityResponse getValidity(@RequestHeader("Authorization") final String token);
}
