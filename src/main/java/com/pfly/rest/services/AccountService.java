package com.pfly.rest.services;

import java.util.List;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Account;

public interface AccountService {
	
	public Account createAccount(Account account) throws AppException;
	
	public List<Account> getAccounts() throws AppException;
	
	public Account getAccountById(Long accountId) throws AppException;

}