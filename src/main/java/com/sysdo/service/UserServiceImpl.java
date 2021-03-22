package com.sysdo.service;

import com.sysdo.repository.RoleRepository;
import com.sysdo.repository.UserRepository;
import com.sysdo.model.Role;
import com.sysdo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Implementation of UserService and UserDetailsService interfaces
 * */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String USER_ROLE = "USER";


    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String input){
        User user;
        if (input.contains("@"))
            user = findByEmail(input);
        else user = findByUsername(input);

        if (user == null)
            throw new UsernameNotFoundException("Cannot find user");
        else return new UserDetailsImpl(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public String registration(User user) {

        userCheck(user);
        user = roleCheck(user);
        user.setEnabled(false);
        user.setActivation(keyGenerator());
        userRepository.save(user);

        return "messages.registrationSuccess";
    }

    private void userCheck(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new GlobalThrowableExcaption("error.emailAlreadyExists");
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new GlobalThrowableExcaption("error.usernameAlreadyExists");
        if (user.getPassword().compareTo(user.getConfirmPassword()) != 0)
            throw new GlobalThrowableExcaption("error.mismatchPasswords");
    }

    private User roleCheck(User user) {
        Role userRole = roleRepository.findByRole(USER_ROLE);

        if (userRole != null) user.getRoles().add(userRole);
        else user.addRoles(USER_ROLE);

        return user;
    }


    public String keyGenerator(){
        Random random = new Random();
        char[] key = new char[16];
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) ('a' + random.nextInt(26));
        }
        return new String(key);
    }

    @Override
    public String userActivation(String code) {
        User user = userRepository.findByActivation(code);

        if (user == null)
            return "noresult";

        user.setEnabled(true);
        user.setActivation("");
        userRepository.save(user);

        return "messages.activationSuccess";
    }

}
