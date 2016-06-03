package com.pfly.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pfly.persistence.entity.Account;
import com.pfly.persistence.entity.Task;
import com.pfly.util.GcmHelper;

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

	public void updateTask(Task task) {
		entityManager.merge(task);

	}

	public Task getTaskById(Long id) {
		Task task = entityManager.find(Task.class, id);
		System.out.println("id:" + task.getTaskId());
		return task;
	}

	public List<Task> searchTasksByName(String searchForName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> query = cb.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);
		// ParameterExpression<String> nameExpr = cb.parameter(String.class);
		query.where(cb.like(root.<String> get("name"),
				cb.parameter(String.class, "param")));

		Expression<Long> categoryId = root.get("category");
		query.groupBy(categoryId);
		TypedQuery<Task> tq = entityManager.createQuery(query);
		tq.setParameter("param", "%" + searchForName + "%");

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

}
