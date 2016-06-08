package com.pfly.persistence.dao;

import java.util.List;

import com.pfly.persistence.entity.Task;

public interface TaskDAO {
	
	public  List<Task> getTasks();
	
	public  List<Task> getTasksByAccount(Long accountId, Boolean getClosedTasks);
	
	public  Task addTask (Task task);
	
	public  void updateTask (Task task);
	
	public Task getTaskById(Long id);
	
	public void deleteTask(Task task);

	public List<Task> getTasksByProject(Long projectId);

}
