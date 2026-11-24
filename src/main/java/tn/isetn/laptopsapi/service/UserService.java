package tn.isetn.laptopsapi.service;

import tn.isetn.laptopsapi.entities.Role;
import tn.isetn.laptopsapi.entities.User;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    void addRoleToUser(String username, String roleName);
}
