package com.pfly.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pfly.errors.AppException;
import com.pfly.persistence.dao.AccountDAO;
import com.pfly.persistence.entity.Account;

public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDAO accountDao;
	
	@Override
	public Account createAccount(Account account) throws AppException {
		System.out.println("add acount");
		return accountDao.addAccount(account);
	}

	@Override
	public List<Account> getAccounts() throws AppException {
		return accountDao.getAccounts();
	}

	@Override
	public Account getAccountById(Long accountId) throws AppException {
		return accountDao.getAccountById(accountId);
	}
	
	
	
}
