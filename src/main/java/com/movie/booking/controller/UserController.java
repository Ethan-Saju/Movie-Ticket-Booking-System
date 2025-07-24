package com.movie.booking.controller;

import com.movie.booking.configs.AppConstants;
import com.movie.booking.models.AppUser;
import com.movie.booking.payloads.*;
import com.movie.booking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getAllUsers(
        @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
        @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
        @RequestParam(name="sortBy" , defaultValue = "userId") String sortBy,
        @RequestParam(name="sortOrder" , defaultValue = AppConstants.SORT_ORDER) String sortOrder
    ) {
        return new ResponseEntity<>(userService.getUsers(pageNumber,pageSize,sortBy,sortOrder), HttpStatus.OK);
    }


    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }


    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userId, userDTO), HttpStatus.OK);
    }


    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }


    @PostMapping("/public/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return new ResponseEntity<>(userService.createUser(userCreateDTO), HttpStatus.CREATED);
    }


    @GetMapping("/users/me")
    public ResponseEntity<UserDTO> getMyProfile() {
        return new ResponseEntity<>(userService.getLoggedInUserProfile(), HttpStatus.OK);
    }


    @PutMapping("/users/me")
    public ResponseEntity<UserDTO> updateMyProfile(@RequestBody UserUpdateDTO user) {
        return new ResponseEntity<>(userService.updateMyProfile(user), HttpStatus.OK);
    }


    @DeleteMapping("/users/me")
    public ResponseEntity<APIResponse> deleteMyProfile() {
        return new ResponseEntity<>(userService.deleteMyProfile(), HttpStatus.OK);
    }

    @PutMapping("/users/me/password")
    public ResponseEntity<APIResponse> changeMyPassword(@RequestBody ChangePasswordDTO dto) {
        return new ResponseEntity<>(userService.changeMyPassword(dto), HttpStatus.OK);
    }

}

