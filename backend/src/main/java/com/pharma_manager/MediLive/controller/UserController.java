package com.pharma_manager.MediLive.controller;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.UserEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import com.pharma_manager.MediLive.mapper.DoctorMapper;
import com.pharma_manager.MediLive.mapper.UserMapper;
import com.pharma_manager.MediLive.service.DoctorService;
import com.pharma_manager.MediLive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public DoctorService doctorService;

    @Autowired
    public UserService userService;


    @GetMapping("/get-doctor")
    public ResponseEntity<DoctorDto> getDoctorInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserDto userDto =  userService.findByUserName(userName);

        return new ResponseEntity<>(doctorService.getDoctorById(userDto.getDoctorAssigned()), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userInfo) {

        // get username from SecurityContextHolder which stores authenticated username
        // after they are registered automatically via create endpoint
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserDto updatedUser = userService.updateUser(userName, userInfo);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        userService.deleteByUserName(userName);

        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/get-nurse")
    public ResponseEntity<List<NurseDto>> getNurseForDay() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return new ResponseEntity<>(userService.getNurseForDay(userName), HttpStatus.OK);
    }

    @GetMapping("/get-doctor/{department}")
    public ResponseEntity<DoctorDto> getEmergencyDoctor(@PathVariable("department") String department) {
        //TODO return doctor if already assigned
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            return new ResponseEntity<>(userService.getEmergencyDoctor(userName, department), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("All the doctors r in an emergency");
        }
    }
}
