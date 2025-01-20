package com.Payvang.Login.Services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Payvang.Login.Constants.UserType;
import com.Payvang.Login.DataAccess.Models.MerchantSignup;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.Roles;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Util.ErrorType;



@Service
public class AccountsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AccountsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailId(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }else {
        	 User retrievedUser = user.get();
     //   	 Set<Roles> role = (retrievedUser.getRoles() != null && !retrievedUser.getRoles().isEmpty()) ? retrievedUser.getRoles() : "USER";
        	UserType roles = retrievedUser.getUserType();
        	String role = roles.name();
        	 return org.springframework.security.core.userdetails.User.builder()
                     .username(retrievedUser.getEmailId())
                     .password(retrievedUser.getPassword())
                     .roles(role) // Set the role (if null or empty, it will be "USER")
                     .build();
        }

        // Handle null or empty role, and assign a default role if necessary

       
    }
    

}
