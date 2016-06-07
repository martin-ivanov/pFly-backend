package com.pfly.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.pfly.persistence.entity.Project;

public class ProjectDAOImpl implements ProjectDAO {

	@PersistenceContext(unitName = "pflyEntityManager")
	private EntityManager entityManager;
	
	@Transactional
	public List<Project> getProjects() {
		String sqlString = "SELECT u FROM Project u";
		TypedQuery<Project> query = entityManager.createQuery(sqlString,
				Project.class);
		return query.getResultList();
	}

	@Transactional
	public Project addProject(Project project) {
		// System.out.println("adding project " + project.getFullName());
		entityManager.merge(project);
		entityManager.flush();
		return project;
	}

	public void updateProject(Project project) {
		entityManager.merge(project);

	}

	@Transactional
	public Project getProjectById(Long id) {
		Project project = entityManager.find(Project.class, id);
		return project;
	}
}
