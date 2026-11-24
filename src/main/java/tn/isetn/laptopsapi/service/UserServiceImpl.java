package tn.isetn.laptopsapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.isetn.laptopsapi.entities.Role;
import tn.isetn.laptopsapi.entities.User;
import tn.isetn.laptopsapi.repos.RoleRepository;
import tn.isetn.laptopsapi.repos.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override @Transactional(readOnly = true) public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    @Override public Role addRole(Role role) { return roleRepository.save(role); }
    @Override public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Role role = roleRepository.findByRole(roleName).orElseThrow();
        if (user.getRoles().stream().noneMatch(existing -> existing.getRole().equals(roleName))) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }
}
