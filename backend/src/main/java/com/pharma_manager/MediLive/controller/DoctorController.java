package com.pharma_manager.MediLive.controller;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.DoctorEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import com.pharma_manager.MediLive.mapper.DoctorMapper;
import com.pharma_manager.MediLive.service.DoctorService;
import com.pharma_manager.MediLive.service.DoctorService;
import com.pharma_manager.MediLive.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    public DoctorService doctorService;

    @GetMapping("/patient")
    public ResponseEntity<UserDto> getAllottedPatientInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return new ResponseEntity<>(doctorService.getPatientInfo(userName), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDto doctorInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        DoctorDto updatedDoctor = doctorService.updateDoctor(userName, doctorInfo);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        doctorService.deleteByUserName(userName);

        return new ResponseEntity<>("Doctor deleted successfully.", HttpStatus.OK);
    }
}
