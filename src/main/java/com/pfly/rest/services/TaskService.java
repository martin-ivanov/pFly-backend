package com.pfly.rest.services;

import java.util.List;

import com.pfly.errors.AppException;
import com.pfly.persistence.entity.Task;

public interface TaskService {
	
	public Task createTask(Task task) throws AppException;

	public Task getTaskById(Long id) throws AppException;
	
	public List<Task> getTasks() throws AppException;

	
	public void deleteTask(Task task) throws AppException;
	
	public void declineTask(Long id, String action) throws AppException;

	public List<Task> getTasksByProject(Long projectId);

	public List<Task> getTasksByAccount(Long accountId, Boolean getClosedTasks);
		
}