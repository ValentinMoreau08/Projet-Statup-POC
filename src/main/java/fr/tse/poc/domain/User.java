package fr.tse.poc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User {

	private @Id @GeneratedValue Long id;
	private String login;
	private String password;
	private String name;
	private String firstname;
	
	@JsonIgnoreProperties("managed")
	@ManyToOne
	private User manager;
	
	@JsonIgnoreProperties("manager")
	@OneToMany(mappedBy="manager", fetch=FetchType.EAGER)
	private Set<User> managed;
	
	@JsonIgnoreProperties("user")
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<Time> times;
	
	@ManyToOne
	private Role role;
	
	public User() {
		times = new HashSet<Time>();
		managed = new HashSet<User>();
	}
	
	public User(String login, String password, String name, String firstname, Role role) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.firstname = firstname;
		this.role = role;
		times = new HashSet<Time>();
		managed = new HashSet<User>();
	}

	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getFirstname() {return firstname;}
	public void setFirstname(String firstname) {this.firstname = firstname;}
	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}
	public User getManager() {return manager;}
	public void setManager(User manager) {this.manager = manager;}
	
	public Set<User> getManaged() {return managed;}
	public void setManaged(Set<User> managed) {this.managed = managed;}
	public void addManaged(User user) {this.managed.add(user);}
	public void deleteManaged(User user) {this.managed.remove(user);}
	
	public Set<Time> getTimes() {return times;}
	public void setTimes(Set<Time> times) {this.times = times;}
	public void addTime(Time time) {this.times.add(time);}
	public void deleteTime(Time time) {this.times.remove(time);}

	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (managed == null) {
			if (other.managed != null)
				return false;
		} else if (managed.size() != other.managed.size())
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (times.size() != other.times.size())
			return false;
		return true;
	}

	
	
	
	
}
