package com.pfly.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pfly.errors.AppException;
import com.pfly.persistence.dao.AccountDAO;
import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;
import com.pfly.rest.security.TokenUtils;
import com.pfly.rest.security.pFlyUser;

public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDAO accountDao;

	@Autowired
	TaskService taskService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	public Account createAccount(pFlyUser user)
			throws AppException {
		System.out.println("add acount");
		String password = user.getPassword();
		String encryptedPassword = passwordEncoder.encode(password);
		user.setPassword(encryptedPassword);
		return accountDao.addAccount(user.getAccount());
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
	public List<Task> getTasksByAccount(Long accountId, Boolean getClosedTasks)
			throws AppException {
		List<Task> accountTasks = taskService.getTasksByAccount(accountId,
				getClosedTasks);
		return accountTasks;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Account account = accountDao.getAccountByName(username);

		if (account == null) {
			throw new UsernameNotFoundException(String.format(
					"No user found with username '%s'.", username));
		} else {
			return new pFlyUser(account);
		}
	}

	@Override
	public String loginUser(String username, String password)
			throws AppException {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				username, password, null);

		Authentication authentication = this.authenticationManager
				.authenticate(authToken);

		// Reload password post-authentication so we can generate token
		UserDetails userDetails = loadUserByUsername(username);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String tokenString = tokenUtils.generateToken(userDetails);
		System.out.println("Token is: " + tokenString);

		return tokenString;
	}

}
