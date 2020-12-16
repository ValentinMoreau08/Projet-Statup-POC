package fr.tse.poc.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class User extends Utilisateur {

	@ManyToOne
	private Manager manager;
	
	public User() {
		
	}
	
	public User(String login, String password, String name, String firstname, Manager manager) {
		super(login, password, name, firstname);
		this.manager = manager;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	

	
	
}
