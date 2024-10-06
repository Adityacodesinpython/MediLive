package com.pharma_manager.MediLive.service.impl;

import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.NurseEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import com.pharma_manager.MediLive.mapper.NurseMapper;
import com.pharma_manager.MediLive.mapper.UserMapper;
import com.pharma_manager.MediLive.repository.NurseRepository;
import com.pharma_manager.MediLive.repository.UserRepository;
import com.pharma_manager.MediLive.service.NurseService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service    // creates a bean/instance of the service to use
@AllArgsConstructor
public class NurseServiceImpl implements NurseService {
    
    public NurseRepository nurseRepository;

    public UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // authenticated/hashed method
    @Override
    public NurseDto saveNewNurse(NurseDto nurseDto) {
        NurseEntity nurseEntity = NurseMapper.mapToNurseEntity(nurseDto);

        nurseEntity.setPassWord(passwordEncoder.encode(nurseEntity.getPassWord()));
        nurseEntity.setRoles(new String[]{"NURSE"});

        nurseRepository.save(nurseEntity);

        return NurseMapper.mapToNurseDto(nurseEntity);
    }

    @Override
    public UserDto getPatientInfo(String userName) {
        NurseEntity nurseEntity = nurseRepository.findByUserName(userName);

        return UserMapper.mapToUserDto(nurseEntity.getUserEntity());
    }
    @Transactional
    @Override
    public NurseDto setPatientInfo(String userName, NurseDto updateNurseInfo) {
        NurseEntity foundNurse = nurseRepository.findByUserName(userName);

        foundNurse.setUserEntity(updateNurseInfo.getUserEntity());

        return NurseMapper.mapToNurseDto(nurseRepository.save(foundNurse));
    }

    @Override
    public NurseDto findByUserName(String userName) {
        return NurseMapper.mapToNurseDto(nurseRepository.findByUserName(userName));
    }

    @Override
    public List<NurseDto> getAllNurses() {
        List<NurseEntity> allNursesEntity = nurseRepository.findAll();
        return allNursesEntity.stream().map((oneNurseEntity) -> NurseMapper.mapToNurseDto(oneNurseEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<NurseDto> getAllDayShiftNurses(String day) {
        List<NurseEntity> allNursesEntity = nurseRepository.findAll();
        List<NurseDto> result = new ArrayList<>();

        for (NurseEntity nurse : allNursesEntity) {
            if (Arrays.asList(nurse.getDayShifts()).contains(day)) {
                result.add(NurseMapper.mapToNurseDto(nurse));
            }
        }

        return result;
    }

    @Override
    public List<NurseDto> getAllNightShiftNurses(String day) {
        List<NurseEntity> allNursesEntity = nurseRepository.findAll();
        List<NurseDto> result = new ArrayList<>();

        for (NurseEntity nurse : allNursesEntity) {
            if (Arrays.asList(nurse.getNightShifts()).contains(day)) {
                result.add(NurseMapper.mapToNurseDto(nurse));
            }
        }

        return result;
    }

    @Override
    public NurseDto getNurseById(Long nurseId) {

        NurseEntity foundNurse = nurseRepository.findById(nurseId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no nurse with the ID: " + nurseId));

        return NurseMapper.mapToNurseDto(foundNurse);

    }

    @Override
    public NurseDto updateNurse(String userName, NurseDto updateNurseInfo) {

        NurseEntity foundNurse = nurseRepository.findByUserName(userName);

        foundNurse.setUserName(updateNurseInfo.getUserName() == null ? foundNurse.getUserName() : updateNurseInfo.getUserName());
        foundNurse.setPassWord(updateNurseInfo.getPassWord() == null ? foundNurse.getPassWord() :passwordEncoder.encode(updateNurseInfo.getPassWord()));
        foundNurse.setDayShifts(updateNurseInfo.getDayShifts() == null ? foundNurse.getDayShifts() :updateNurseInfo.getDayShifts());
        foundNurse.setNightShifts(updateNurseInfo.getNightShifts() == null ? foundNurse.getNightShifts() :updateNurseInfo.getNightShifts());
        foundNurse.setRoles(updateNurseInfo.getRoles() == null ? foundNurse.getRoles() :updateNurseInfo.getRoles());
        foundNurse.setUserEntity(updateNurseInfo.getUserEntity() == null ? foundNurse.getUserEntity() : updateNurseInfo.getUserEntity());

        NurseEntity updatedNurse = nurseRepository.save(foundNurse);

        return NurseMapper.mapToNurseDto(updatedNurse);
    }
    @Transactional
    @Override
    public void deleteByUserName(String userName) {
        nurseRepository.deleteByUserName(userName);
    }

}
