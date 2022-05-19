package com.pensionmanagement.authorization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pensionmanagement.authorization.config.JwtAuthenticationEntryPoint;
import com.pensionmanagement.authorization.config.JwtRequestFilter;
import com.pensionmanagement.authorization.config.JwtTokenUtil;
import com.pensionmanagement.authorization.controller.JwtAuthenticationController;
import com.pensionmanagement.authorization.model.JwtRequest;
import com.pensionmanagement.authorization.model.JwtResponse;
import com.pensionmanagement.authorization.model.UserModel;
import com.pensionmanagement.authorization.repository.UserRepository;
import com.pensionmanagement.authorization.service.JwtUserDetailsService;
import com.pensionmanagement.authorization.service.LoginService;
import com.pensionmanagement.authorization.service.Validationservice;

@TestPropertySource("/application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorizationApplicationTests {

	public String token;
	private MockMvc mockMvc;
	static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext wc;

	@Autowired
	private JwtAuthenticationController controller;

	@MockBean
	private HttpServletRequest request;

	@Mock
	private AuthenticationException authException;

	@MockBean
	private HttpServletResponse response;

	@InjectMocks
	private JwtAuthenticationEntryPoint uut;

	@MockBean
	UserDetails userDetails;

	@Autowired
	private JwtUserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@MockBean
	FilterChain chain;

	@Mock
	JwtRequest jwtRequest;

	@Mock
	UserModel userModel;

	@Mock
	JwtResponse jwtResponse;

	@Autowired
	Validationservice validationService;

	@Autowired
	LoginService loginService;

	@Before
	public void setUp() throws JsonProcessingException, Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
		login();
	}

	@Test
	public void login() throws JsonProcessingException, Exception {
		UserModel menu = new UserModel("Raagavi", "qwe!123");
		String json = mapper.writeValueAsString(menu);
		MvcResult andReturn = mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate").content(json)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful()).andReturn();
		parseResponse(andReturn);
	}

	@Test
	public void getValidate() throws JsonProcessingException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/validateToken").header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testFilter() throws ServletException, IOException {
		JwtRequestFilter filter = new JwtRequestFilter();
		when(request.getHeader("Authorization")).thenReturn(token);
		filter.doFilter(request, response, chain);
	}

	@Test
	public void mapsToError() throws Exception {
		uut.commence(request, response, authException);
		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

	@Test
	public void validateToken() {
		jwtTokenUtil.getIssuedAtDateFromToken(token);
		jwtTokenUtil.getExpirationDateFromToken(token);
		jwtTokenUtil.getUsernameFromToken(token);
	}

	@Test
	@DisplayName("This method is responsible to test isTokenExpiredOrInvalidFormat()")
	public void testIsTokenExpiredOrInvalidFormat_validToken() {
		userDetails = jwtInMemoryUserDetailsService.loadUserByUsername("Raagavi");
		userRepository.findByUsername("Raagavi");
		String token1 = jwtTokenUtil.generateToken(userDetails);
		assertNotNull(token1);
	}

	@Test
	public void testJwtRequest() {
		jwtRequest = new JwtRequest("Raagavi", "Password");
		jwtRequest.setUsername("Naveen");
		jwtRequest.setPassword("Password");

		jwtRequest.getUsername();
		jwtRequest.getPassword();
		new JwtRequest();

		userModel = new UserModel("Raagavi", "Password");
		userModel.setUsername("Naveen");
		userModel.setPassword("Password");
		userModel.setId(1l);
		userModel.getId();
		userModel.getUsername();
		userModel.getPassword();
		new UserModel();
	}

	@Test
	public void testJwtResponse() {
		jwtResponse = new JwtResponse(
				"eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzE4MTgsInN1YiI6ImFkbWluMSIsImV4cCI6MTYyNzAzMTg3OH0.iBDf8UvcnHKa-TVHHxjOQUiC3oEVGgsYrJSvD5LhUQc",
				true);

		jwtResponse.getToken();
		jwtResponse.isValid();

		JwtResponse jwtResponse1 = new JwtResponse();
		jwtResponse1.setValid(false);
	}

	@Test(expected = BadCredentialsException.class)
	public void throwException() {
		loginService.authenticate("username", "password");
	}

	@Test
	public void testValidationService() {
		validationService.validate("sampletoken");
	}

	@Test
	public void testJwtAuthenticationController() throws Exception {
		loginService.authenticate("Raagavi", "qwe!123");
	}

	@Test
	public void TestController() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void jwtUtilNotNull() {
		assertNotNull(jwtTokenUtil);
	}

	public String parseResponse(MvcResult result)
			throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		String contentAsString = result.getResponse().getContentAsString();
		String[] values = contentAsString.split(",");
		String tokenLength;
		if (values[0].startsWith("{\"t")) {
			tokenLength = values[0].substring(10);
			token = tokenLength.substring(0, tokenLength.length() - 1);
		} else {
			tokenLength = values[1].substring(9);
			token = tokenLength.substring(0, tokenLength.length() - 2);
		}
		return token;
	}

}
