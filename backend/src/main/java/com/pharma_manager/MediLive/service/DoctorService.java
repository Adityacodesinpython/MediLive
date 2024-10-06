package com.pharma_manager.MediLive.service;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DoctorService {
    DoctorDto findByUserName(String userName);

    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(Long doctorId);

    DoctorDto updateDoctor(String userName, DoctorDto updateDoctorInfo);

    UserDto getPatientInfo(String userName);

    DoctorDto saveNewDoctor(DoctorDto doctorDto);

    DoctorDto setPatientInfo(String userName, DoctorDto updateDoctorInfo);

    void deleteByUserName(String userName);
}
