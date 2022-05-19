package com.pensionmanagement.processpension.repository;

import com.pensionmanagement.processpension.model.ProcessPensionInput;

public interface ProcessPensionInputRepository {

	public void saveRequest(ProcessPensionInput request);
}
