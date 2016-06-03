package com.pfly.rest.services;

import java.util.List;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Project;

public interface ProjectService {
	
	public Project createProject(Project project) throws AppException;

	public Project getProjectById(Long id) throws AppException;	
	
	public List<Project> getProjects() throws AppException;

}