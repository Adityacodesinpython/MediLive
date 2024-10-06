package com.pharma_manager.MediLive.service.impl;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.DoctorEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import com.pharma_manager.MediLive.mapper.DoctorMapper;
import com.pharma_manager.MediLive.mapper.UserMapper;
import com.pharma_manager.MediLive.repository.DoctorRepository;
import com.pharma_manager.MediLive.repository.UserRepository;
import com.pharma_manager.MediLive.service.DoctorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service    // creates a bean/instance of the service to use
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    public DoctorRepository doctorRepository;

    public UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // authenticated/hashed method
    @Override
    public DoctorDto saveNewDoctor(DoctorDto doctorDto) {
        DoctorEntity doctorEntity = DoctorMapper.mapToDoctorEntity(doctorDto);

        doctorEntity.setPassWord(passwordEncoder.encode(doctorEntity.getPassWord()));
        doctorEntity.setRoles(new String[]{"DOCTOR"});

        doctorRepository.save(doctorEntity);

        return DoctorMapper.mapToDoctorDto(doctorEntity);
    }

    @Override
    public UserDto getPatientInfo(String userName) {
        DoctorEntity doctorEntity = doctorRepository.findByUserName(userName);

        return UserMapper.mapToUserDto(doctorEntity.getUserEntity());
    }

    @Override
    public DoctorDto findByUserName(String userName) {
        return DoctorMapper.mapToDoctorDto(doctorRepository.findByUserName(userName));
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        List<DoctorEntity> allDoctorsEntity = doctorRepository.findAll();
        return allDoctorsEntity.stream().map((oneDoctorEntity) -> DoctorMapper.mapToDoctorDto(oneDoctorEntity))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto getDoctorById(Long doctorId) {

        DoctorEntity foundDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no doctor with the ID: " + doctorId));

        return DoctorMapper.mapToDoctorDto(foundDoctor);

    }

    @Override
    public DoctorDto updateDoctor(String userName, DoctorDto updateDoctorInfo) {

        DoctorEntity foundDoctor = doctorRepository.findByUserName(userName);

        foundDoctor.setUserName(updateDoctorInfo.getUserName() == null ? foundDoctor.getUserName() : updateDoctorInfo.getUserName());
        foundDoctor.setPassWord(updateDoctorInfo.getPassWord() == null ? foundDoctor.getPassWord() : passwordEncoder.encode(updateDoctorInfo.getPassWord()));
        foundDoctor.setDepartment(updateDoctorInfo.getDepartment() == null ? foundDoctor.getDepartment() :updateDoctorInfo.getDepartment());
        foundDoctor.setIsAvailable(updateDoctorInfo.getIsAvailable() == null ? foundDoctor.getIsAvailable() :updateDoctorInfo.getIsAvailable());
        foundDoctor.setInAnEmergency(updateDoctorInfo.getInAnEmergency() == null ? foundDoctor.getInAnEmergency() :updateDoctorInfo.getInAnEmergency());
        foundDoctor.setUserEntity(updateDoctorInfo.getUserEntity() == null ? foundDoctor.getUserEntity() :updateDoctorInfo.getUserEntity());
        foundDoctor.setRoles(updateDoctorInfo.getRoles() == null ? foundDoctor.getRoles() :updateDoctorInfo.getRoles());

        DoctorEntity updatedDoctor = doctorRepository.save(foundDoctor);

        return DoctorMapper.mapToDoctorDto(updatedDoctor);
    }

    @Override
    public DoctorDto setPatientInfo(String userName, DoctorDto updateDoctorInfo) {

        DoctorEntity foundDoctor = doctorRepository.findByUserName(userName);

        foundDoctor.setUserEntity(updateDoctorInfo.getUserEntity());

        return DoctorMapper.mapToDoctorDto(doctorRepository.save(foundDoctor));
    }

    @Transactional
    @Override
    public void deleteByUserName(String userName) {
        doctorRepository.deleteByUserName(userName);
    }

}
