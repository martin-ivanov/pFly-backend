package com.pfly.persistence.dao;

import java.util.List;

import com.pfly.persistence.entity.Project;

public interface ProjectDAO {
	
	public abstract List<Project> getProjects();
	
	public abstract Project addProject (Project project);

	public abstract void updateProject (Project Project);
	
	public Project getProjectById(Long id);

}
