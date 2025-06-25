package java.com.clouddeployment.service;

import java.com.clouddeployment.model.Role;
import java.com.clouddeployment.model.User;
import java.com.clouddeployment.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
/** {@author waheedk} !*/
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    /** userRepository !*/
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) 
    				throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core
        		   .userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
