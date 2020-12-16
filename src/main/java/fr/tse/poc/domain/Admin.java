package fr.tse.poc.domain;

import javax.persistence.Entity;

@Entity
public class Admin extends Utilisateur{

	public Admin() {
		super();
	}

	public Admin(String login, String password, String name, String firstname) {
		super(login, password, name, firstname);
	}

}
