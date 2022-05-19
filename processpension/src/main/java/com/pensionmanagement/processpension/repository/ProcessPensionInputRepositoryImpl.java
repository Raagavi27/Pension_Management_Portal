package com.pensionmanagement.processpension.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pensionmanagement.processpension.model.ProcessPensionInput;

@Repository
public class ProcessPensionInputRepositoryImpl implements ProcessPensionInputRepository {

	@Autowired
	EntityManager em;

	@Transactional
	public void saveRequest(ProcessPensionInput request) {
		em.persist(request);
	}
}
