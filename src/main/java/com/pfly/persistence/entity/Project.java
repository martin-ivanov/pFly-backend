package com.pfly.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name="project")
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@prjId")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long projectId;
	private Long creatorId;
	private Date dateCreated;
	private String description;
	private String name;
	private List<Task> tasks;

	public Project() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_id", unique=true, nullable=false)
	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}


	@Column(name="creator_id", nullable=false)
	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	@Column(length=300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="project", fetch=FetchType.EAGER)
//	@JsonBackReference
	@JsonIgnore
	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setProject(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setProject(null);

		return task;
	}

}