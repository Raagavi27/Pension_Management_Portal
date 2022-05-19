package com.pensionmanagement.pensionerdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.pensionmanagement.pensionerdetail.service.BankDetailService;
import com.pensionmanagement.pensionerdetail.service.PensionerDetailService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class PensionerdetailApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private BankDetailService bankRepositoryService;

	@Autowired
	private PensionerDetailService pensionerDetailService;

	public static void main(String[] args) {
		SpringApplication.run(PensionerdetailApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Loading bank details into database...");
		bankRepositoryService.loadPensionerDetailsToDatabase();
		log.info("Loading pensioner details into database...");
		pensionerDetailService.loadPensionerDetails();
	}
}
