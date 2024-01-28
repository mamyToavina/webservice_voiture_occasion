/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.service;

import com.webserviceVoiture.webserviceVoiture.configuration.user_model.Role;
import com.webserviceVoiture.webserviceVoiture.configuration.user_model.User;
import com.webserviceVoiture.webserviceVoiture.repository.RoleRepository;
import com.webserviceVoiture.webserviceVoiture.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author TOAVINA
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
      
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User findById(String id){
        try {
            Long userId = Long.parseLong(id);
            Optional<User> userOptional = findById(userId);
            return userOptional.orElse(null);
        } catch (NumberFormatException e) {
            System.out.println("L'ID n'est pas un nombre valide : " + id);
            return null;
        }
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(defaultRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }
    
    public Long getUserIdByUsernameAndPassword(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passwordEncoder.matches(password, user.getPassword())){
                return user.getId();
            }
        }
        return null;
    }
}

