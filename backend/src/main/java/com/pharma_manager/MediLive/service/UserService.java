package com.pharma_manager.MediLive.service;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    UserDto findByUserName(String userName);

    UserDto getUserById(Long userId);

    UserDto updateUser(String username, UserDto updateUserInfo);

    void deleteByUserName(String userName);

    UserDto saveNewUser(UserDto userDto);

    List<NurseDto> getNurseForDay(String userName);

    DoctorDto getEmergencyDoctor(String userName, String department);
}
