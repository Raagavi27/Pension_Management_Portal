package com.pensionmanagement.pensionerdetail.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pensionmanagement.pensionerdetail.model.BankDetail;
import com.pensionmanagement.pensionerdetail.model.CustomErrorHandler;
import com.pensionmanagement.pensionerdetail.model.PensionerDetail;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PensionerDetailRepositoryImpl implements PensionerDetailRepository {

	@Autowired
	private BankDetailRepositoryImpl bankRepository;

	@Autowired
	EntityManager em;

	public void loadPensionerDetails() {
		List<PensionerDetail> pensionerDetail = new ArrayList<>();

		InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream("pensionerdetails.csv");
		try (InputStreamReader isr = new InputStreamReader(ioStream); BufferedReader br = new BufferedReader(isr);) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				Long aadhaarNumber = Long.parseLong(values[0]);
				String name = values[1];
				String dateOfBirth = values[2];
				String pan = values[3];
				BigDecimal salaryEarned = BigDecimal.valueOf(Long.parseLong(values[4]));
				BigDecimal allowances = BigDecimal.valueOf(Long.parseLong(values[5]));
				String typeOfPension = values[6];
				Long bankId = Long.parseLong(values[7]);
				BankDetail bankDetail = null;
				bankDetail = bankRepository.getBankDetailsById(bankId);
				PensionerDetail detail = new PensionerDetail(aadhaarNumber, name, dateOfBirth, pan, salaryEarned,
						allowances, typeOfPension, bankDetail);
				pensionerDetail.add(detail);
				em.persist(detail);
			}
			ioStream.close();
		} catch (IOException e) {
			log.warn(e.getMessage());
		}
	}

	public PensionerDetail getPensionerDetails(Long aadhaarNumber) {
		try {
			return em.find(PensionerDetail.class, aadhaarNumber);
		} catch (Exception e) {
			log.warn("Unable to fetch pensioner detail");
			throw new CustomErrorHandler("Unable to fetch pensioner detail");
		}
	}
}
