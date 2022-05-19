package com.pensionmanagement.pensionerdetail.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pensionmanagement.pensionerdetail.model.BankDetail;
import com.pensionmanagement.pensionerdetail.model.CustomErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BankDetailRepositoryImpl implements BankDetailRepository {
	@Autowired
	EntityManager em;

	public void loadPensionerDetailsToDatabase() {

		List<BankDetail> bankdetails = new ArrayList<>();

		InputStream ioStream1 = this.getClass().getClassLoader().getResourceAsStream("bankdetails.csv");
		try (InputStreamReader isr = new InputStreamReader(ioStream1); BufferedReader br = new BufferedReader(isr);) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				String bankName = values[0];
				Long accountNumber = Long.parseLong(values[1]);
				String bankType = values[2];
				BankDetail bankdetail = new BankDetail(bankName, accountNumber, bankType);
				bankdetails.add(bankdetail);
				em.persist(bankdetail);
			}
			ioStream1.close();
		} catch (NumberFormatException e) {
			log.warn(e.getMessage());
			throw new CustomErrorHandler(e.getMessage());
		} catch (IOException e) {
			log.warn(e.getMessage());
			throw new CustomErrorHandler(e.getMessage());
		}

	}

	public BankDetail getBankDetailsById(Long bankId) {
		return em.find(BankDetail.class, bankId);
	}
}