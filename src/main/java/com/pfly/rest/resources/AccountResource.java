package com.pfly.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;
import com.pfly.rest.services.AccountService;

@Component
@Path("/accounts")
public class AccountResource {

	@Autowired
	private AccountService accountService;	

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createAccount(@RequestBody Account account) throws AppException {
		
		Account createAccount = accountService.createAccount(account);
		return Response.status(Response.Status.CREATED)// 201
				.entity(createAccount).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response getAccounts() throws IOException, AppException {
		System.out.println("getAccounts");
		List<Account> acccounts = accountService.getAccounts();
		return Response.status(200).entity(acccounts)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();

	}
	
	@GET
	@Path("/{id}/tasks")
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response getTasksByAccount(@PathParam("id") Long accountId, @QueryParam("getClosedTasks") Boolean getClosedTasks) throws IOException, AppException {
		System.out.println("getTasksByAccount");
		List<Task> tasks = accountService.getTasksByAccount(accountId, getClosedTasks);
		
		return Response.status(200).entity(tasks)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();

	}


}