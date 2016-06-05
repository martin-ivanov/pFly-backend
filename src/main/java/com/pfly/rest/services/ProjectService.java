package com.pfly.rest.services;

import java.util.List;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Project;
import com.pfly.persistence.entity.Task;

public interface ProjectService {
	
	public Project createProject(Project project) throws AppException;

	public Project getProjectById(Long id) throws AppException;	
	
	public List<Project> getProjects() throws AppException;

	public List<Task> getTasksByProjectId(Long projectId);

}