package com.pfly.persistence.dao;

import java.util.List;

import com.pfly.persistence.entity.Task;

public interface TaskDAO {
	
	public abstract List<Task> getTasks();
	
	public abstract Task addTask (Task task);
	
	public abstract void updateTask (Task task);
	
	public Task getTaskById(Long id);
	
	public List<Task> searchTasksByName(String searchForName);
	
	public void deleteTask(Task task);

}
