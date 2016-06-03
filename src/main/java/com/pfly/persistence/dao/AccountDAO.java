package com.pfly.persistence.dao;

import java.util.List;

import com.pfly.persistence.entity.Account;

public interface AccountDAO {
	
	public abstract List<Account> getAccounts();
	
	public abstract Account addAccount (Account account);
	
	public abstract void updateAccount (Account account);
	
	public Account getAccountById(Long id);

	public Account getAccountByCredentials(String accountname, String password);
}
