package com.pfly.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pfly.errors.AppException;
import com.pfly.persistence.dao.AccountDAO;
import com.pfly.persistence.dao.TaskDAO;
import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;

public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDAO accountDao;
	
	@Autowired
	TaskDAO taskDao;
	
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
	
	@Override
	public List<Task> getTasksByAccount(Long accountId) throws AppException {
		List<Task> accountTasks = taskDao.getTasksByAccount(accountId);
		return accountTasks;
	}
	
	
	
}
