package com.sweet_company.simplyPay.controller;

import com.sweet_company.simplyPay.domain.user.UserEntity;
import com.sweet_company.simplyPay.dto.UserDto;
import com.sweet_company.simplyPay.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDto userDto){
        UserEntity user = this.userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUser(){
        List<UserEntity> allUsers = this.userService.findAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
}
