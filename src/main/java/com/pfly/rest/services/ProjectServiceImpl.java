package com.pfly.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pfly.errors.AppException;
import com.pfly.persistence.dao.ProjectDAO;
import com.pfly.persistence.dao.TaskDAO;
import com.pfly.persistence.entity.Project;
import com.pfly.persistence.entity.Task;

public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDAO projectDao;
	
	@Autowired
	TaskDAO taskDao;
	
	@Override
	public Project createProject(Project project)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getProjectById(Long id) throws AppException {
		Project responseEntity = projectDao.getProjectById(id);
		return responseEntity;
	}

	@Override
	public List<Project> getProjects() throws AppException {
		List<Project> responseEntities = projectDao.getProjects();
		return responseEntities;
	}

	@Override
	public List<Task> getTasksByProjectId(Long projectId) {
		List<Task> projectTasks = taskDao.getTasksByProject(projectId);
		return projectTasks;
	}
}
