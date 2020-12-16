package fr.tse.poc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Manager extends Utilisateur {

	@OneToMany(mappedBy="manager", fetch=FetchType.EAGER)
	private Set<User> users;
	
	public Manager() {
		
	}
	
	public Manager(String login, String password, String name, String firstname) {
		super(login, password, name, firstname);
		this.users = new HashSet<User>();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj))
			return false;
		Manager other = (Manager) obj;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	
	

}
