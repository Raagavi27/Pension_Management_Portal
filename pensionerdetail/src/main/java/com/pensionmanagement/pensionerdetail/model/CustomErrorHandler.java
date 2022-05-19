package com.pensionmanagement.pensionerdetail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomErrorHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String message;
}
