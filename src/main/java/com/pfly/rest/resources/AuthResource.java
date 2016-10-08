package com.pfly.rest.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Account;
import com.pfly.rest.security.pFlyUser;
import com.pfly.rest.services.AccountService;

@Path("/auth")
public class AuthResource {

	@Autowired
	private AccountService accountService;

	@POST
	@Path("/register")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createAccount(@RequestBody pFlyUser user)
			throws AppException {
		String password = user.getPassword();
		Account createAccount = accountService.createAccount(user);
		String token = accountService.loginUser(user.getName(), password);
		user.setAccountId(createAccount.getAccountId());
		user.setToken(token);
		user.setPassword(null);
		return Response.status(Response.Status.CREATED)// 201
				.entity(user).build();
	}

	
	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response login(@RequestBody pFlyUser user) throws IOException,
			AppException {
		System.out.println("login");

		try {
			String token = accountService.loginUser(user.getName(),
					user.getPassword());
			return Response.status(200).entity(token)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		} catch (AuthenticationException authEx) {
			return Response.status(401).entity(authEx.getMessage())
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		}
	}

}
