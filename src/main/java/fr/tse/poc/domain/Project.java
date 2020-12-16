package fr.tse.poc.domain;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Project {

    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String clientName;
    private String description;

    public Project() {
    }

    public Project(String name, String clientName, String description) {
        this.name = name;
        this.clientName = clientName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
