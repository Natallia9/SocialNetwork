package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.UserDTO;
import com.example.socialnetwork.entity.User;
import com.example.socialnetwork.entity.enums.ERole;
import com.example.socialnetwork.exceptions.UserExistException;
import com.example.socialnetwork.payload.request.SignupRequest;
import com.example.socialnetwork.repository.UserRepository;
import com.example.socialnetwork.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserHelperService userHelperService;

    public void createUser(SignupRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setFirstName(userIn.getFirstname());
        user.setLastName(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try{
            LOG.info("Saving User {}", userIn.getEmail());
            userRepository.save(user);
        } catch (Exception e){
            LOG.error("Error during registration {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + "already exist");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal){

        User user = userHelperService.getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal){

        return userHelperService.getUserByPrincipal(principal);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
