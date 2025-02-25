package com.example.socialnetwork.controllers;

import com.example.socialnetwork.dto.UserDTO;
import com.example.socialnetwork.entity.User;
import com.example.socialnetwork.mapper.UserMapper;
import com.example.socialnetwork.service.UserService;
import com.example.socialnetwork.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userMapper.toDto(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId){
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userMapper.toDto(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userUpdate = userMapper.toDto(user);

        return new ResponseEntity<>(userUpdate, HttpStatus.OK);

    }
}
