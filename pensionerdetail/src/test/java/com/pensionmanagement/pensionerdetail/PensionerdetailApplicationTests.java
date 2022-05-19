package com.pensionmanagement.pensionerdetail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pensionmanagement.pensionerdetail.controller.PensionerDetailController;
import com.pensionmanagement.pensionerdetail.feign.AuthenticationProxy;
import com.pensionmanagement.pensionerdetail.model.BankDetail;
import com.pensionmanagement.pensionerdetail.model.PensionerDetail;
import com.pensionmanagement.pensionerdetail.model.ValidityResponse;
import com.pensionmanagement.pensionerdetail.repository.PensionerDetailRepository;
import com.pensionmanagement.pensionerdetail.repository.PensionerDetailRepositoryImpl;
import com.pensionmanagement.pensionerdetail.service.BankDetailServiceImpl;
import com.pensionmanagement.pensionerdetail.service.PensionerDetailService;
import com.pensionmanagement.pensionerdetail.service.PensionerDetailServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@TestPropertySource("/application.properties")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PensionerdetailApplicationTests {

	@Value("${spring.application.name}")
	private String appname;
	@Autowired
	private MockMvc mvc;

	@Autowired
	PensionerDetailRepositoryImpl pensionerDetailRepositoryImpl;

	@Autowired
	private BankDetailServiceImpl bankDetailServiceImpl;
	
	@MockBean
	private PensionerDetailController controller;

	@MockBean
	PensionerDetailService pensionerDetailService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wc;

	@MockBean
	private AuthenticationProxy proxy;
	
	@Mock
	PensionerDetailRepository pensionerDetailRepository;

	@InjectMocks
	PensionerDetailServiceImpl pensionerDetailServiceImpl;

	BankDetail bankDetails;


	@Before
	public void setUp1() throws JsonProcessingException, Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
	}

	@Test
	public void testController() throws Exception {
		ValidityResponse details = new ValidityResponse("", true);
		when(proxy.getValidity("token")).thenReturn(details);
		PensionerDetail detail = new PensionerDetail();
		when(pensionerDetailService.getPensionerDetails(530048102447l)).thenReturn(detail);
		mockMvc.perform(get("/PensionerDetailByAadhaar?aadhaarNumber=530048102447").header("Authorization", "token"))
				.andExpect(status().isOk());
		verify(controller, timeout(1)).pensionerDetailByAadhaar("token", 530048102447l);
	}

	@Test
	public void invalidParamPassTest() throws Exception {
		String values = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSYWFnYXZpIiwiZXhwIjoxNjUyMTkyODQxLCJpYXQiOjE2NTIxOTEwNDF9.U9tKmQ7F9bQ-qxytjlwfMglyiHJMrbrs3NoB32CYQm4";
		mockMvc.perform(MockMvcRequestBuilders.get("/PensionerDetailByAadhaar?aadhaarNumber=").header("Authorization",
				"Bearer " + values)).andExpect(status().is4xxClientError());
	}

	@Test
	public void testGetRequest() throws Exception {
		String values = "";
		mvc.perform(MockMvcRequestBuilders.get("/PensionerDetailByAadhaar?aadhaarNumber=").header("Authorization",
				"Bearer " + values)).andExpect(status().isBadRequest());
	}

	@Test
	public void testGetRequest2() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/PensionerDetailByAadhaar?aadhaarNumber=123"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testMock() throws Exception {
		BigDecimal bigDecimal1 = new BigDecimal("12030");
		BankDetail bankDetail = new BankDetail("HDFC Bank", 1242101027280l, "Private");
		PensionerDetail pensionerDetail = new PensionerDetail(234l, "Naveen", "25/05/1999", "476", bigDecimal1,
				bigDecimal1, "Self pension", bankDetail);
		when(pensionerDetailRepository.getPensionerDetails(123l)).thenReturn(pensionerDetail);
		Assertions.assertEquals("476", pensionerDetailServiceImpl.getPensionerDetails(123l).getPan(),
				"Pan number doesn't exist");
		verify(pensionerDetailRepository, times(1)).getPensionerDetails(123l);
	}

	@Test
	public void TestToken() {
		ValidityResponse validityResponse = new ValidityResponse();
		String token1 = "";
		validityResponse.setValid(true);
		validityResponse.setToken(token1);
		assertEquals(validityResponse.getToken(), token1);
	}

	
	@Test
	public void TestgetBankDetailsById() {
		bankDetails = bankDetailServiceImpl.getBankDetailsById(3l);
		assertEquals(252095369596196l, bankDetails.getAccountNumber(), "test fails");
		assertNotEquals(1242102033l, bankDetails.getAccountNumber(), "test fails");
		Assertions.assertEquals("State Bank of India", bankDetails.getBankName());

	}

	@Test
	@DisplayName("Testing Bank Details")
	public void TestgetBankDetailsNull() {
		bankDetails = bankDetailServiceImpl.getBankDetailsById(43l);
		assertNull(bankDetails, "Object should be null");
		bankDetails = bankDetailServiceImpl.getBankDetailsById(3l);
		assertNotNull(bankDetails, "Object should be null");
	}
	
	@Test
	public void TestBankDetails() {

		BankDetail bankDetail3 = new BankDetail();
		bankDetail3 = null;

		bankDetail.getBankName();
		bankDetail.getId();
		bankDetail.getTypeOfBank();
		bankDetail.getAccountNumber();

		bankDetail.setAccountNumber(123428920l);
		bankDetail.setBankName("HDFC Bank");
		bankDetail.setTypeOfBank("Private");
		bankDetail.toString();

		Assertions.assertEquals("HDFC Bank", bankDetail.getBankName(), "Pan number doesn't exist");
		Assertions.assertNotEquals("Public", bankDetail.getTypeOfBank(), "TypeOfBank doesn't match");
		Assertions.assertEquals(123428920l, bankDetail.getAccountNumber(), "Account number doesn't exist");
		Assertions.assertNotSame(bankDetail, bankDetail2, " two variables not refer to the same object");
		Assertions.assertNull(bankDetail3);
		Assertions.assertNotNull(bankDetail);

	}

	PensionerDetail pensionerDetail1 = new PensionerDetail();
	PensionerDetail pensionerDetail2 = pensionerDetail1;
	PensionerDetail pensionerDetail3 = null;

	BankDetail bankDetail = new BankDetail();
	BankDetail bankDetail2 = new BankDetail("HDFC Bank", 1242101027280l, "Private");

	BigDecimal Salary = new BigDecimal("13420");
	BigDecimal Allowances = new BigDecimal("14000");

	@Test
	public void TestPensionerDetails() throws Exception {

		pensionerDetail1 = pensionerDetailRepositoryImpl.getPensionerDetails(530048102447l);
		pensionerDetail1.getAadhaarNumber();
		pensionerDetail1.getName();
		pensionerDetail1.getDateOfBirth();
		pensionerDetail1.getSalaryEarned();
		pensionerDetail1.getTypeOfPension();
		pensionerDetail1.getAllowances();
		bankDetail = pensionerDetail1.getBankDetail();

		pensionerDetail1.setName("Radha");
		pensionerDetail1.setAadhaarNumber(152l);
		pensionerDetail1.setPan("345");
		pensionerDetail1.setSalaryEarned(Salary);
		pensionerDetail1.setAllowances(Allowances);
		pensionerDetail1.setDateOfBirth("15/05/1994");
		pensionerDetail1.setTypeOfPension("Self");
		pensionerDetail1.setBankDetail(bankDetail2);

		ReflectionTestUtils.setField(pensionerDetail1, "pan", "457");

		Boolean bool = false;
		if (pensionerDetail1.getPan().equals("457")) {
			bool = true;
		}

		Assertions.assertEquals("457", pensionerDetail1.getPan(), "Pan number doesn't match");
		Assertions.assertEquals("Radha", pensionerDetail1.getName(), "Name doesn't match");
		Assertions.assertNotEquals("456", pensionerDetail1.getPan(), "Pan number is matching");
		Assertions.assertNotSame(pensionerDetail1, pensionerDetail2, " two variables not refer to the same object");
		Assertions.assertNull(pensionerDetail3);
		Assertions.assertNotNull(pensionerDetail1);
		Assertions.assertTrue(bool);

		pensionerDetailService.getPensionerDetails(132l);

	}

	@Test
	public void TestController() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void applicationContextTest() {
		PensionerdetailApplication.main(new String[] {});
		log.info("Application is running");
	}

	@AfterClass
	public static void setAfterclass() {
		log.info("Testing is completed");
	}

}
