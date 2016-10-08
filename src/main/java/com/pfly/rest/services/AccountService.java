package com.pfly.rest.services;

import java.util.List;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;
import com.pfly.rest.security.pFlyUser;

public interface AccountService extends UserDetailsService {
		
	public Account createAccount(pFlyUser user) throws AppException;
	
	public List<Account> getAccounts() throws AppException;
	
	public Account getAccountById(Long accountId) throws AppException;

	public List<Task> getTasksByAccount(Long accountId, Boolean getClosedTasks) throws AppException;
	
	public String loginUser(String username, String password) throws AuthenticationException, AppException;

}