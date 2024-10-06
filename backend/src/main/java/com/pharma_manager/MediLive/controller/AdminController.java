package com.pharma_manager.MediLive.controller;

import com.pharma_manager.MediLive.dto.AdminDto;
import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.service.AdminService;
import com.pharma_manager.MediLive.service.DoctorService;
import com.pharma_manager.MediLive.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private NurseService nurseService;
    @Autowired
    public DoctorService doctorService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> getALlUsers() {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/all-nurses")
    public ResponseEntity<List<NurseDto>> getAllNurses() {
        return new ResponseEntity<>(nurseService.getAllNurses(), HttpStatus.OK);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        return new ResponseEntity<>(adminService.createAdmin(adminDto), HttpStatus.CREATED);
    }

    @PostMapping("/create-nurse")
    public ResponseEntity<NurseDto> createNurse(@RequestBody NurseDto nurseDto) {
        return new ResponseEntity<>(nurseService.saveNewNurse(nurseDto), HttpStatus.CREATED);
    }

    @PostMapping("/create-doctor")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto) {
        return new ResponseEntity<>(doctorService.saveNewDoctor(doctorDto), HttpStatus.CREATED);
    }

    @GetMapping("/get-doctor/{id}")
    public ResponseEntity<DoctorDto> getSpecificDoctor(@PathVariable("id") Long doctorId) {
        return new ResponseEntity<>(doctorService.getDoctorById(doctorId), HttpStatus.OK);
    }

    @GetMapping("/get-nurse/{id}")
    public ResponseEntity<NurseDto> getSpecificNurse(@PathVariable("id") Long nurseId) {
        return new ResponseEntity<>(nurseService.getNurseById(nurseId), HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminInfo) {

        // get username from SecurityContextHolder which stores authenticated username
        // after they are registered automatically via create endpoint
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        AdminDto updatedAdmin = adminService.updateAdmin(userName, adminInfo);

        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        adminService.deleteByUserName(userName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
