package com.pensionmanagement.processpension.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pensionmanagement.processpension.model.PensionDetail;

@Repository
public class PensionDetailRepositoryImpl implements PensionDetailRepository {

	@Autowired
	EntityManager em;

	@Transactional
	public void saveResponse(PensionDetail detail) {
		em.persist(detail);
	}

}
