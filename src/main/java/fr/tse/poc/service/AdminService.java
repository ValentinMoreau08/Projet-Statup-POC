package fr.tse.poc.service;

import fr.tse.poc.domain.Admin;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.User;
import fr.tse.poc.domain.Utilisateur;

public interface AdminService {

    public Admin findAdminById(Long id);
    public Admin createAdmin(String login, String password, String name, String firstname);
    public User changeManagerToUser(Manager manager);
    public Admin changeManagerToAdmin(Manager manager);
    public Manager changeUserToManager(User user);
    public Admin changeUserToAdmin(User user);
    public Manager changeAdminToManager(Admin admin);
    public User changeAdminToUser(Admin admin);

}
