package com.pfly.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
		// System.out.println("id:" + task.getTaskId());
		return task;
	}

	public List<Task> getTasksByAccount(Long accountId, Boolean getClosedTasks) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> query = cb.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);

		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(cb.or(cb.equal(root.get("account").get("accountId"),
				cb.parameter(Long.class, "param0")),
				cb.equal(root.get("transferedTo"),
						cb.parameter(Long.class, "param1")),
				cb.equal(root.get("delegatedTo"),
						cb.parameter(Long.class, "param2"))));

		if (getClosedTasks != null) {
			if (getClosedTasks) {
				predicates.add(cb.and(cb.equal(root.get("status"),
						cb.parameter(Integer.class, "param3"))));
			} else {
				predicates.add(cb.and(cb.notEqual(root.get("status"),
						cb.parameter(Integer.class, "param3"))));
			}
		}

		query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<Task> tq = entityManager.createQuery(query);
		tq.setParameter("param0", accountId);
		tq.setParameter("param1", accountId);
		tq.setParameter("param2", accountId);

		Set<Parameter<?>> params = tq.getParameters();
		System.out.println(params);
		for (Parameter<?> param : params) {
			if (param.getName().equals("param3")) {
				tq.setParameter("param3", 4);
				break;
			}
		}

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

		query.where(cb.equal(root.get("project").get("projectId"),
				cb.parameter(Long.class, "param")));

		TypedQuery<Task> tq = entityManager.createQuery(query);
		tq.setParameter("param", projectId);

		return tq.getResultList();
	}
}
