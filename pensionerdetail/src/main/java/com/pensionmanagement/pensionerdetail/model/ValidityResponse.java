package com.pensionmanagement.pensionerdetail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidityResponse {
	private String token;
	private boolean isValid;
}
