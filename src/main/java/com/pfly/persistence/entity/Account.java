package com.pfly.persistence.entity;

import java.io.Serializable;
import java.math.BigInteger;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the accounts database table.
 * 
 */
@Entity
@Table(name="accounts")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@userId")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long accountId;
	private String deviceId;
	private String email;
	private String name;
	private String userId;
	private List<Task> tasks;

	public Account() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="account_id", unique=true, nullable=false)
	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	@Column(name="device_id", nullable=false)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	@Column(nullable=false, length=100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(nullable=false, length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name="user_id", nullable=false)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="account", fetch=FetchType.EAGER)
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
		task.setAccount(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setAccount(null);

		return task;
	}

}