package com.pfly.rest.services;

import java.util.List;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;

public interface AccountService {
	
	public Account createAccount(Account account) throws AppException;
	
	public List<Account> getAccounts() throws AppException;
	
	public Account getAccountById(Long accountId) throws AppException;	

	public List<Task> getTasksByAccount(Long accountId) throws AppException;

}