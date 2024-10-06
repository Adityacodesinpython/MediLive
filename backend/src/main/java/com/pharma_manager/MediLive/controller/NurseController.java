package com.pharma_manager.MediLive.controller;

import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @GetMapping("/patient")
    public ResponseEntity<UserDto> getAllottedPatientInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return new ResponseEntity<>(nurseService.getPatientInfo(userName), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<NurseDto> updateNurse(@RequestBody NurseDto nurseInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        NurseDto updatedNurse = nurseService.updateNurse(userName, nurseInfo);
        return new ResponseEntity<>(updatedNurse, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteNurse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        nurseService.deleteByUserName(userName);

        return new ResponseEntity<>("Nurse deleted successfully.", HttpStatus.OK);
    }
}
