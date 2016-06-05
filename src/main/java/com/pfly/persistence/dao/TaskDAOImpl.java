package com.pfly.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pfly.persistence.entity.Task;

public class TaskDAOImpl implements TaskDAO {

	@PersistenceContext(unitName = "pflyEntityManager")
	private EntityManager entityManager;

	@Autowired
	ProjectDAO projectDao;

	@Autowired
	AccountDAO accountDao;

	public List<Task> getTasks() {
		String sqlString = "SELECT t FROM Task t";
		TypedQuery<Task> query = entityManager.createQuery(sqlString,
				Task.class);
		return query.getResultList();
	}

	@Transactional
	public Task addTask(Task task) {

		entityManager.merge(task);
		entityManager.flush();
		return task;
	}

	@Transactional
	public void updateTask(Task task) {
		entityManager.merge(task);
		entityManager.flush();

	}

	public Task getTaskById(Long id) {
		Task task = entityManager.find(Task.class, id);
//		System.out.println("id:" + task.getTaskId());
		return task;
	}

	public List<Task> getTasksByAccount(Long accountId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> query = cb.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);
		
		query.where(
				cb.or(
					cb.equal(root.get("account").get("accountId"), cb.parameter(Long.class, "param0")),
					cb.equal(root.get("transferedTo"), cb.parameter(Long.class, "param1")),
					cb.equal(root.get("delegatedTo"), cb.parameter(Long.class, "param2"))
					)
				);

		TypedQuery<Task> tq = entityManager.createQuery(query);
		tq.setParameter("param0", accountId);
		tq.setParameter("param1", accountId);
		tq.setParameter("param2", accountId);

		return tq.getResultList();
	}

	public List<Task> getTasksByCategory(Long categoryId) {
		List<Task> recipies = entityManager
				.createQuery(
						"SELECT u FROM Task u where u.categoryId = :categoryIdValue",
						Task.class).setParameter("categoryIdValue", categoryId)
				.getResultList();
		return recipies;

	}

	@Override
	@Transactional
	public void deleteTask(Task task) {
		entityManager.remove(entityManager.merge(task));
	}

	@Override
	public List<Task> getTasksByProject(Long projectId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> query = cb.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);
		
		query.where(
				cb.equal(root.get("project").get("projectId"), cb.parameter(Long.class, "param"))
		);

		TypedQuery<Task> tq = entityManager.createQuery(query);
		tq.setParameter("param", projectId);

		return tq.getResultList();
	}
}
