package com.pfly.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the task database table.
 * 
 */
@Entity
@Table(name="task")
@NamedQuery(name="Task.findAll", query="SELECT t FROM Task t")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@tskId")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long taskId;
	private String name;
	private String notes;
	private String description;
	private String desiredOutcome;
	private Integer eventId;
	private Integer flyScore;
	private Integer intImportance;
	private Integer extImportance;
	private Integer clearness;
	private Integer simplicity;
	private Integer closeness;
	private Date dateCreated;
	private Date dateFinished;
	private Date deadline;
	private Date lastResponsibleMoment;

	private Integer status;
	private Integer recommendedAction;
	
	private Integer takenAction;
	private Long transferedTo;
	private Long delegatedTo;
	private Long dependOn;

	private Account account;
	private Project project;

	public Task() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="task_id", unique=true, nullable=false)
	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public Integer getCloseness() {
		return this.closeness;
	}

	public void setCloseness(Integer closeness) {
		this.closeness = closeness;
	}
	
	public Integer getClearness() {
		return this.clearness;
	}

	public void setClearness(Integer clearness) {
		this.clearness = clearness;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_finished")
	public Date getDateFinished() {
		return this.dateFinished;
	}

	public void setDateFinished(Date dateFinished) {
		this.dateFinished = dateFinished;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}


	@Column(name="delegated_to")
	public Long getDelegatedTo() {
		return this.delegatedTo;
	}

	public void setDelegatedTo(Long delegatedTo) {
		this.delegatedTo = delegatedTo;
	}


	@Column(name="depend_on")
	public Long getDependOn() {
		return this.dependOn;
	}

	public void setDependOn(Long dependOn) {
		this.dependOn = dependOn;
	}


	@Column(length=300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="event_id")
	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}


	@Column(name="desired_outcome")
	public String getDesiredOutcome() {
		return this.desiredOutcome;
	}

	public void setDesiredOutcome(String desiredOutcome) {
		this.desiredOutcome = desiredOutcome;
	}


	@Column(name="fly_score")
	public Integer getFlyScore() {
		return this.flyScore;
	}

	public void setFlyScore(Integer flyScore) {
		this.flyScore = flyScore;
	}

	@Column(name="int_importance")
	public Integer getIntImportance() {
		return this.intImportance;
	}

	public void setIntImportance(Integer intImportance) {
		this.intImportance = intImportance;
	}
	
	@Column(name="ext_importance")
	public Integer getExtImportance() {
		return this.extImportance;
	}

	public void setExtImportance(Integer extImportance) {
		this.extImportance = extImportance;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_responsible_moment")
	public Date getLastResponsibleMoment() {
		return this.lastResponsibleMoment;
	}

	public void setLastResponsibleMoment(Date lastResponsibleMoment) {
		this.lastResponsibleMoment = lastResponsibleMoment;
	}


	@Column(length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	@Column(name="recommended_action", length=500)
	public Integer getRecommendedAction() {
		return this.recommendedAction;
	}

	public void setRecommendedAction(Integer recommendedAction) {
		this.recommendedAction = recommendedAction;
	}


	public Integer getSimplicity() {
		return this.simplicity;
	}

	public void setSimplicity(Integer simplicity) {
		this.simplicity = simplicity;
	}


	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	@Column(name="taken_action", length=500)
	public Integer getTakenAction() {
		return this.takenAction;
	}

	public void setTakenAction(Integer takenAction) {
		this.takenAction = takenAction;
	}


	@Column(name="transfered_to")
	public Long getTransferedTo() {
		return this.transferedTo;
	}

	public void setTransferedTo(Long transferedTo) {
		this.transferedTo = transferedTo;
	}


	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="creator_id", nullable=false)
//	@JsonManagedReference
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
//	@JsonManagedReference
	public Project getProject() {
		return this.project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

}