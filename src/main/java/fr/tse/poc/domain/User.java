package fr.tse.poc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User {

	private @Id @GeneratedValue Long id;
	private String login;
	private String password;
	private String name;
	private String firstname;
	
	@ManyToOne
	private User manager;
	
	@OneToMany(mappedBy="manager", fetch=FetchType.EAGER)
	private Set<User> managed;
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((managed == null) ? 0 : managed.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((times == null) ? 0 : times.hashCode());
		return result;
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
