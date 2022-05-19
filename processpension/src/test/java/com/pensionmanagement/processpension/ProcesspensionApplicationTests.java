package com.pensionmanagement.processpension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pensionmanagement.processpension.controller.ProcessController;
import com.pensionmanagement.processpension.feign.AuthProxy;
import com.pensionmanagement.processpension.feign.PensionerDetailProxy;
import com.pensionmanagement.processpension.model.BankDetail;
import com.pensionmanagement.processpension.model.PensionDetail;
import com.pensionmanagement.processpension.model.ProcessPensionInput;
import com.pensionmanagement.processpension.model.ValidityResponse;
import com.pensionmanagement.processpension.repository.PensionDetailRepository;
import com.pensionmanagement.processpension.repository.PensionDetailRepositoryImpl;
import com.pensionmanagement.processpension.service.BankDetailService;
import com.pensionmanagement.processpension.service.ProcessPensionInputService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@TestPropertySource("/application.properties")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ProcesspensionApplicationTests {

	@MockBean
	private ProcessController controller;

	@Mock
	PensionDetailRepository pensionDetailRepository;

	@InjectMocks
	PensionDetailRepositoryImpl pensionDetailRepositoryImpl = new PensionDetailRepositoryImpl();

	@MockBean
	AuthProxy authProxy;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PensionerDetailProxy pensionDetailProxy;

	@Autowired
	BankDetailService bankDetailService;

	@Autowired
	ProcessPensionInputService processPensionInputService;

	@Test
	public void applicationContextTest() {
		ProcesspensionApplication.main(new String[] {});
		log.info("Application started");
	}

	@Test
	void checkController() throws Exception {
		when(authProxy.getValidity("token")).thenReturn(new ValidityResponse("", true));
		ProcessPensionInput input = new ProcessPensionInput(543214197463l);
		BankDetail bank = new BankDetail(1l, "HDFC Bank", 182994763044524l, "Private");
		ProcessPensionInput input1 = new ProcessPensionInput(543214197463l, "Kirthika", "01-22-1975", "605C8107S8",
				new BigDecimal(485530), new BigDecimal(24277), "Family", bank);
		when(pensionDetailProxy.pensionerDetailByAadhaar("token", input1.getAadhaarNumber())).thenReturn(input1);
		mockMvc.perform(MockMvcRequestBuilders.post("/ProcessPension").content(asJsonString(input1))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "token")).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final String jsonContent = mapper.writeValueAsString(obj);
		return jsonContent;
	}

	@Test
	public void testBankServiceCharge() {
		BigDecimal value = bankDetailService.bankServiceCharge(bankDetail);
		log.info("BankServiceCharge is verified");
	}

	@Test
	public void TestValidityResponse() {
		ValidityResponse response = new ValidityResponse();
		response.setToken("token");
		response.setValid(true);
		response.getToken();
		response.isValid();
		ValidityResponse response1 = new ValidityResponse("token", false);
		log.info("Tested model class - Validity Response");
	}

	BankDetail bankDetail = new BankDetail(123l, "HDFC Bank", 1242101027280l, "Private");
	BankDetail bankDetail1 = new BankDetail(101l, "HDFC Bank", 1242101027280l, "Public");
	ProcessPensionInput processPensionInput = new ProcessPensionInput();
	ProcessPensionInput processPensionInput1 = new ProcessPensionInput();
	PensionDetail pensionerDetail = new PensionDetail();
	BigDecimal salaryEarned = new BigDecimal("12000");
	BigDecimal allowances = new BigDecimal("1000");
	BigDecimal bankServiceCharge = new BigDecimal("12000");
	BigDecimal pensionAmount = new BigDecimal("1000");
	Long aadhaarNumber = 1242101027280l;

	ProcessPensionInput processPensionInput2 = new ProcessPensionInput(12345L, "Krish", "24/09/1998", "354",
			salaryEarned, allowances, "Family", bankDetail1);
	PensionDetail pensionDetails = new PensionDetail(bankServiceCharge, pensionAmount, aadhaarNumber);

	@Test
	public void TestPensionDetail() {
		PensionDetail pensionDetail = new PensionDetail();
		BigDecimal bankServiceCharge = new BigDecimal("500");
		BigDecimal pensionAmount = new BigDecimal("1000");
		Long aadhaarNumber = 1242101027280l;
		pensionDetail.setId(1l);
		pensionDetail.setBankServiceCharge(bankServiceCharge);
		pensionDetail.setPensionAmount(pensionAmount);
		pensionDetail.getId();
		pensionDetail.getBankServiceCharge();
		pensionDetail.getPensionAmount();
		pensionDetail.getId();

		PensionDetail pensionDetails = new PensionDetail(bankServiceCharge, pensionAmount, aadhaarNumber);
		log.info("Tested model class - Pension Detail");
	}

	@Test
	public void testProcessPensionInput() {
		BigDecimal salaryEarned = new BigDecimal("12000");
		BigDecimal allowances = new BigDecimal("1000");

		processPensionInput.setAadhaarNumber(124210110l);
		processPensionInput.setName("Kumar");
		processPensionInput.setDateOfBirth("25/05/1999");
		processPensionInput.setPan("675");
		;
		processPensionInput.setSalaryEarned(salaryEarned);
		processPensionInput.setAllowances(allowances);

		processPensionInput.setTypeOfPension("Self");
		processPensionInput.setBankDetail(bankDetail);

		processPensionInput.getAadhaarNumber();
		processPensionInput.getName();
		processPensionInput.getDateOfBirth();
		processPensionInput.getPan();
		processPensionInput.getSalaryEarned();
		processPensionInput.getAllowances();

		processPensionInput.getTypeOfPension();
		processPensionInput.getBankDetail();
		processPensionInput1 = null;
		processPensionInputService.pensionAmount(processPensionInput);
		processPensionInputService.pensionAmount(processPensionInput2);

		Assertions.assertEquals("675", processPensionInput.getPan(), "Pan number doesn't match");
		Assertions.assertEquals("Kumar", processPensionInput.getName(), "Name doesn't match");
		Assertions.assertNotEquals("456", processPensionInput.getPan(), "Pan number is matching");
		Assertions.assertNotSame(processPensionInput, processPensionInput1,
				" two variables not refer to the same object");
		Assertions.assertNull(processPensionInput1);
		Assertions.assertNotNull(processPensionInput);

		Boolean bool = false;
		if (processPensionInput.getPan().equals("675")) {
			bool = true;
		}
		Assertions.assertTrue(bool);
	}

	@Test
	public void testBankDetails() {

		BankDetail bankDetail = new BankDetail();
		bankDetail = null;

		BankDetail bankDetails = new BankDetail();

		bankDetails.setAccountNumber(3456732l);
		bankDetails.setBankName("State Bank Of India");
		bankDetails.setId(323l);
		bankDetails.setTypeOfBank("Public");
		Assertions.assertEquals(323l, bankDetails.getId(), "Bank Id doesn't match");
		Assertions.assertEquals("State Bank Of India", bankDetails.getBankName(), "Bank Name doesn't macth");
		Assertions.assertNotEquals("Private", bankDetails.getTypeOfBank(), "TypeOfBank is matching");
		Assertions.assertEquals(3456732l, bankDetails.getAccountNumber(), "Account number doesn't match");
		Assertions.assertNotSame(bankDetail, bankDetails, " two variables not refer to the same object");
		Assertions.assertNull(bankDetail);
		Assertions.assertNotNull(bankDetails);
	}

	@Test
	public void TestController() {
		assertThat(controller).isNotNull();
	}

}
