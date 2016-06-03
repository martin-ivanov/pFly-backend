package com.pfly.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.pfly.persistence.entity.Task;
import com.pfly.rest.services.AccountService;
import com.pfly.rest.services.ProjectService;
import com.pfly.rest.services.TaskService;

@Component
@Path("/tasks")
public class TaskResource {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProjectService projectService;
	

	@Autowired
	private AccountService accountService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON  + ";charset=UTF-8"})
	public Response createTask(@RequestBody Task task) throws AppException {
		System.out.println("create task");
		Task createTask = taskService.createTask(task);
		System.out.println("createdtask>>>>>" + createTask);
		return Response.status(Response.Status.CREATED)// 201
				.entity(createTask).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON  + ";charset=UTF-8"})
	public Response getTaskById(@PathParam("id") Long id) throws IOException,
			AppException {
		System.out.println("getById");
		Task taskById = taskService.getTaskById(id);
		return Response.status(200).entity(taskById)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON  + ";charset=UTF-8" })
	public Response getTasks()
			throws IOException, AppException {
		System.out.println("get tasks");
		List<Task> tasks = taskService.getTasks();
		return Response.status(200).entity(tasks)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response deleteTask(@PathParam("id") Long taskId)
			throws IOException, AppException {
		System.out.println("delete tasks");
	taskService.deleteTask(taskService.getTaskById(taskId));
		return Response.status(200).entity("OK")
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

}