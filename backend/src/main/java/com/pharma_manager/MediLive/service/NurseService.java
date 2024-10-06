package com.pharma_manager.MediLive.service;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public interface NurseService {
    NurseDto setPatientInfo(String userName, NurseDto updateNurseInfo);

    NurseDto findByUserName(String userName);

    List<NurseDto> getAllNurses();

    List<NurseDto> getAllDayShiftNurses(String day);

    List<NurseDto> getAllNightShiftNurses(String day);

    NurseDto getNurseById(Long nurseId);

    NurseDto updateNurse(String userName, NurseDto updateNurseInfo);

    UserDto getPatientInfo(String userName);

    NurseDto saveNewNurse(NurseDto nurseDto);

    void deleteByUserName(String userName);
}
