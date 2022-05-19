package com.pensionmanagement.authorization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pensionmanagement.authorization.model.JwtRequest;
import com.pensionmanagement.authorization.model.JwtResponse;
import com.pensionmanagement.authorization.service.LoginService;
import com.pensionmanagement.authorization.service.Validationservice;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:8200")
@Slf4j
public class JwtAuthenticationController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private Validationservice validationService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> generateAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest)
			throws Exception {
		String token = loginService.checkLogin(authenticationRequest);
		log.info("Valid User. Token has been generated to access the portal.");
		return ResponseEntity.ok(new JwtResponse(token, true));
	}

	@GetMapping("/validateToken")
	public JwtResponse getValidity(@RequestHeader("Authorization") final String token) {
		log.info("Validating token.....");
		return validationService.validate(token);
	}
}
