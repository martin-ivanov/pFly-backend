package com.pfly.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Project;
import com.pfly.persistence.entity.Task;
import com.pfly.rest.services.ProjectService;

@Component
@Path("/projects")
public class ProjectResource {

	@Autowired
	private ProjectService projectService;

	@POST
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response createProject(@FormParam("name") String projectName,
			@FormParam("desc") String projectDesc) throws AppException {
		Project project = new Project();
		project.setName(projectName);
		project.setDescription(projectDesc);
		Project createProject = projectService.createProject(project);
		if (createProject != null) {
			return Response.status(Response.Status.CREATED)// 201
					.entity(createProject).build();
		}

		return Response.status(Response.Status.BAD_REQUEST).build();

	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response getProjectById(@PathParam("id") Long id)
			throws IOException, AppException {
		System.out.println("getById");
		Project projectById = projectService.getProjectById(id);
		if (projectById != null) {
			return Response.status(200).entity(projectById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		}

		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response getProjects() throws IOException, AppException {
		System.out.println("getAll");
		List<Project> categories = projectService.getProjects();
		return Response.status(200).entity(categories)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();

	}

	@GET
	@Path("/{id}/tasks")
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	public Response getProjectRecipes(@PathParam("id") Long projectId)
			throws IOException, AppException {
		System.out.println("getTasksByProject");
		List<Task> projectTasks = projectService.getTasksByProjectId(projectId);

		return Response.status(200).entity(projectTasks)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

}