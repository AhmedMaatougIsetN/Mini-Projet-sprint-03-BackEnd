package tn.isetn.laptopsapi.security;

import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.isetn.laptopsapi.entities.User;
import tn.isetn.laptopsapi.repos.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public MyUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        var authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
            .password(user.getPassword()).authorities(authorities).disabled(!Boolean.TRUE.equals(user.getEnabled())).build();
    }
}
