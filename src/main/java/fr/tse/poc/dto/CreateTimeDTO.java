package fr.tse.poc.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class CreateTimeDTO {

	@NotNull(message = "Time is mandatory")
	private Integer time;
	
	@NotNull(message = "Date is mandatory")
	private @Temporal(TemporalType.TIMESTAMP) Date date;
	
	@NotNull(message = "Project id is mandatory")
	private Long projectId;
	
	
	public CreateTimeDTO() {}

	public CreateTimeDTO(Integer time, Date date, Long projectId) {
		this.time = time;
		this.date = date;
		this.projectId = projectId;
	}

	public Integer getTime() {return time;}
	public void setTime(Integer time) {this.time = time;}
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	public Long getProjectId() {return projectId;}
	public void setProjectId(Long projectId) {this.projectId = projectId;}
	
}
