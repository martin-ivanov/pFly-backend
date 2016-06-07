package com.pfly.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pfly.errors.AppException;
import com.pfly.persistence.dao.TaskDAO;
import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;
import com.pfly.util.GcmHelper;

public class TaskServiceImpl implements TaskService {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	AccountService accountService;

	@Autowired
	TaskDAO taskDao;

	@Override
	public Task createTask(Task task) throws AppException {
		Task responseEntity = taskDao.addTask(task);
		if (task.getDelegatedTo() != null){
			Account delegateUser = accountService.getAccountById(task.getDelegatedTo());
			if (delegateUser != null && delegateUser.getDeviceId() != null) {
				 System.out.println("send notification");
				 GcmHelper.sendNotification(delegateUser.getDeviceId() + "", "delegate",
				 task.getTaskId() + "", task.getName(),
				 task.getDescription());
			}
		}
		
		if (task.getTransferedTo() != null){
			Account transferUser = accountService.getAccountById(task.getTransferedTo());
			if (transferUser != null && transferUser.getDeviceId() != null) {
				 System.out.println("send notification");
				 GcmHelper.sendNotification(transferUser.getDeviceId() + "", "transfer",
				 task.getTaskId() + "", task.getName(),
				 task.getDescription());
			}
		}
		
		
		return responseEntity;
	}

	@Override
	public Task getTaskById(Long id) throws AppException {
		Task response = taskDao.getTaskById(id);
		return response;
	}

	@Override
	public List<Task> getTasks() throws AppException {
		List<Task> response = taskDao.getTasks();
		return response;
	}

	@Override
	public void deleteTask(Task task) throws AppException {
		taskDao.deleteTask(task);
	}

	@Override
	public void declineTask(Long id, String action) throws AppException {
		Task declinedTask = taskDao.getTaskById(id);
		if (declinedTask != null){
			if ("delegate".equalsIgnoreCase(action)){
				declinedTask.setDelegatedTo(null);
			}
			if ("transfer".equalsIgnoreCase(action)){
				declinedTask.setTransferedTo(null);
			}
		taskDao.updateTask(declinedTask);
		}
		
	}

	@Override
	public List<Task> getTasksByProject(Long projectId) {
		return taskDao.getTasksByProject(projectId);
	}

	@Override
	public List<Task> getTasksByAccount(Long accountId) {
		return taskDao.getTasksByAccount(accountId);
	}
	
}