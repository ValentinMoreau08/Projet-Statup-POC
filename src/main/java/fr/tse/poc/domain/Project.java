package fr.tse.poc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project {

    private @Id @GeneratedValue Long id;
    private String name;
    private String clientName;
    private String description;
    
    @OneToMany(mappedBy="project", fetch=FetchType.EAGER)
	private Set<Time> times;

    public Project() {
    	times = new HashSet<Time>();
    }

    public Project(String name, String clientName, String description) {
        this.name = name;
        this.clientName = clientName;
        this.description = description;
        times = new HashSet<Time>();
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getClientName() {return clientName;}
    public void setClientName(String clientName) {this.clientName = clientName;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Set<Time> getTimes() {return times;}
	public void setTimes(Set<Time> times) {this.times = times;}
	public void addTime(Time time) {this.times.add(time);}
	public void deleteTime(Time time) {this.times.remove(time);}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		return true;
	}
	

}
