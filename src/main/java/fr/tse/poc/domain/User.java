package fr.tse.poc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User extends Utilisateur {

	@ManyToOne
	private Manager manager;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<Time> times;
	
	public User() {
		times = new HashSet<Time>();
	}
	
	public User(String login, String password, String name, String firstname) {
		super(login, password, name, firstname);
		times = new HashSet<Time>();
	}

	public Manager getManager() {return manager;}
	public void setManager(Manager manager) {this.manager = manager;}
	public Set<Time> getTimes() {return times;}
	public void setTimes(Set<Time> times) {this.times = times;}
	public void addTime(Time time) {this.times.add(time);}
	public void deleteTime(Time time) {this.times.remove(time);}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		return true;
	}
	
	
	
}
