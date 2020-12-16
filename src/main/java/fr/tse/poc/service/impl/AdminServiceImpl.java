package fr.tse.poc.service.impl;

import fr.tse.poc.dao.AdminRepository;
import fr.tse.poc.dao.ManagerRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Admin;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.User;
import fr.tse.poc.domain.Utilisateur;
import fr.tse.poc.service.AdminService;
import fr.tse.poc.service.ManagerService;
import fr.tse.poc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserService userService;

    @Autowired
    ManagerService managerService;

    @Autowired
    AdminService adminService;

    @Override
    public Admin findAdminById(Long id) {

        return this.adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin createAdmin(String login, String password, String name, String firstname) {
        Admin admin = new Admin(login,password,name,firstname);
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public User changeManagerToUser(Manager manager) {
        User newUser = userService.createUser(manager.getLogin(),manager.getPassword(),manager.getName(),manager.getFirstname());
        managerRepository.delete(manager);
        return newUser;
    }

    @Override
    public Admin changeManagerToAdmin(Manager manager) {
        Admin newAdmin = adminService.createAdmin(manager.getLogin(),manager.getPassword(),manager.getName(),manager.getFirstname());
        managerRepository.delete(manager);
        return newAdmin;
    }

    @Override
    public Manager changeUserToManager(User user) {
        Manager newManager = managerService.createManager(user.getLogin(),user.getPassword(),user.getName(),user.getFirstname());
        userRepository.delete(user);
        return newManager;
    }

    @Override
    public Admin changeUserToAdmin(User user) {
        Admin newAdmin = adminService.createAdmin(user.getLogin(),user.getPassword(),user.getName(),user.getFirstname());
        userRepository.delete(user);
        return newAdmin;
    }

    @Override
    public Manager changeAdminToManager(Admin admin) {
        Manager newManager = managerService.createManager(admin.getLogin(),admin.getPassword(),admin.getName(),admin.getFirstname());
        adminRepository.delete(admin);
        return newManager;
    }

    @Override
    public User changeAdminToUser(Admin admin) {
        User newUser = userService.createUser(admin.getLogin(),admin.getPassword(),admin.getName(),admin.getFirstname());
        adminRepository.delete(admin);
        return newUser;
    }
}
