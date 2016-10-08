package com.pfly.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.pfly.persistence.entity.Account;

public class AccountDAOImpl implements AccountDAO {

	@PersistenceContext(unitName = "pflyEntityManager")
	private EntityManager entityManager;

	public List<Account> getAccounts() {
		String sqlString = "SELECT u FROM Account u";
		TypedQuery<Account> query = entityManager.createQuery(sqlString,
				Account.class);
		return query.getResultList();
	}

	@Transactional
	public Account addAccount(Account account) {
		System.out.println("in addAccount method in DAO");
		Account returnAccount = getAccountByDeviceId(account.getDeviceId(), account.getEmail());
		if (returnAccount == null) {
			returnAccount = entityManager.merge(account);
			entityManager.flush();
		}

		return returnAccount;
	}

	public void updateAccount(Account account) {
		entityManager.merge(account);

	}

	public Account getAccountById(Long id) {
		Account account = entityManager.find(Account.class, id);
		return account;
	}

	public Account getAccountByDeviceId(String deviceId, String email) {
		List<Account> results = entityManager
				.createQuery(
						"SELECT u FROM Account u where u.deviceId = :deviceIdValue and u.email = :emailValue", Account.class)
				.setParameter("deviceIdValue", deviceId)
				.setParameter("emailValue", email)
				.getResultList();
		if (!results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public Account getAccountByCredentials(String accountname, String password) {
		List<Account> accounts = entityManager
				.createQuery(
						"SELECT u FROM Account u where u.accountName = :accountNameValue and u.acc_password=:passwordValue",
						Account.class)
				.setParameter("accountNameValue", accountname)
				.setParameter("passwordValue", password)
				.getResultList();
		if (!accounts.isEmpty()) {
			return accounts.get(0);
		}
		return null;
	}

	@Override
	public Account getAccountByName(String name) {
		List<Account> accounts = entityManager
				.createQuery(
						"SELECT u FROM Account u where u.name = :accountNameValue",
						Account.class)
				.setParameter("accountNameValue", name)
				.getResultList();
		if (!accounts.isEmpty()) {
			return accounts.get(0);
		}
		return null;
	}

}
