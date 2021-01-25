package fr.tse.poc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@Entity
public class Time {
	
    private @Id @GeneratedValue Long id;
    private int time;
    private @Temporal(TemporalType.TIMESTAMP) Date date;
    
    @JsonIgnoreProperties("times")
    private @ManyToOne User user;
    
    @JsonIgnoreProperties("times")
    private @ManyToOne Project project;

    public Time() {}

    public Time(int time, Date date){
        this.time=time;
        this.date=date;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public int getTime() {return time;}
    public void setTime(int time) {this.time = time;}
    public Date getDate() { return date;}
    public void setDate(Date date) {this.date = date; }
	public User getUser() {return user;}
	public void setUser(User user) {this.user = user;}
	public Project getProject() {return project;}
	public void setProject(Project project) {this.project = project;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (time != other.time)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
    
    

}
