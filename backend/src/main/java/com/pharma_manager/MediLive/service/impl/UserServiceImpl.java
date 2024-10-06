package com.pharma_manager.MediLive.service.impl;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.DoctorEntity;
import com.pharma_manager.MediLive.entity.UserEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import com.pharma_manager.MediLive.mapper.UserMapper;
import com.pharma_manager.MediLive.repository.DoctorRepository;
import com.pharma_manager.MediLive.repository.UserRepository;
import com.pharma_manager.MediLive.service.DoctorService;
import com.pharma_manager.MediLive.service.NurseService;
import com.pharma_manager.MediLive.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service    // creates a bean/instance of the service to use
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private DoctorService doctorService;

    private NurseService nurseService;

    @PersistenceContext
    private EntityManager entityManager;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // authenticated/hashed method
    @Override
    public UserDto saveNewUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.mapToUserEntity(userDto);

        userEntity.setPassWord(passwordEncoder.encode(userEntity.getPassWord()));
        userEntity.setRoles(new String[]{"USER"});
        userEntity.setDoctorAssigned(null);
        userEntity.setDayShiftNurseAssigned(null);
        userEntity.setNightShiftNurseAssigned(null);

        userRepository.save(userEntity);

        return UserMapper.mapToUserDto(userEntity);
    }

    @Override
    public UserDto findByUserName(String userName) {
        return UserMapper.mapToUserDto(userRepository.findByUserName(userName));
    }

    @Override
    public UserDto getUserById(Long userId) {

        UserEntity foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no user with the ID: " + userId));

        return UserMapper.mapToUserDto(foundUser);

    }

    @Override
    public UserDto updateUser(String userName, UserDto updateUserInfo) {

        UserEntity foundUser = userRepository.findByUserName(userName);

        foundUser.setUserName(updateUserInfo.getUserName() == null ? foundUser.getUserName() : updateUserInfo.getUserName());
        foundUser.setPassWord(updateUserInfo.getPassWord() == null ? foundUser.getPassWord() : passwordEncoder.encode(updateUserInfo.getPassWord()));
        foundUser.setFirstName(updateUserInfo.getFirstName() == null ? foundUser.getFirstName() : updateUserInfo.getFirstName());
        foundUser.setLastName(updateUserInfo.getLastName() == null ? foundUser.getLastName() : updateUserInfo.getLastName());
        foundUser.setRoles(updateUserInfo.getRoles() == null ? foundUser.getRoles() : updateUserInfo.getRoles());
        foundUser.setDoctorAssigned(updateUserInfo.getDoctorAssigned() == null ? foundUser.getDoctorAssigned() : updateUserInfo.getDoctorAssigned());
        foundUser.setDayShiftNurseAssigned(updateUserInfo.getDayShiftNurseAssigned() == null ? foundUser.getDayShiftNurseAssigned() : updateUserInfo.getDayShiftNurseAssigned());
        foundUser.setNightShiftNurseAssigned(updateUserInfo.getNightShiftNurseAssigned() == null ? foundUser.getNightShiftNurseAssigned() : updateUserInfo.getNightShiftNurseAssigned());

        UserEntity updatedUser = userRepository.save(foundUser);

        return UserMapper.mapToUserDto(updatedUser);
    }
    @Transactional
    @Override
    public void deleteByUserName(String userName) {
        UserEntity foundUser = userRepository.findByUserName(userName);
        DoctorDto assignedDoctor = doctorService.getDoctorById(foundUser.getDoctorAssigned());

        assignedDoctor.setUserEntity(null);
        assignedDoctor.setInAnEmergency("false");
        assignedDoctor.setIsAvailable("true");

        doctorService.updateDoctor(assignedDoctor.getUserName(), assignedDoctor);
        doctorService.setPatientInfo(assignedDoctor.getUserName(), assignedDoctor);

        userRepository.deleteByUserName(userName);

    }



    public DoctorDto getEmergencyDoctor(String userName, String department) {
        List<DoctorDto> doctorsInDeptNotInEmer = new ArrayList<>();
        Random rand = new Random();

        List<DoctorDto> listOfDoctors = doctorService.getAllDoctors();

        for (DoctorDto doctor : listOfDoctors) {
            if (Objects.equals(doctor.getDepartment(), department) && !Objects.equals(doctor.getInAnEmergency(), "true")) {
                doctorsInDeptNotInEmer.add(doctor);
            }
        }

        for (DoctorDto doctor : doctorsInDeptNotInEmer) {
            if (Objects.equals(doctor.getIsAvailable(), "true")) {

                UserEntity foundUser = userRepository.findByUserName(userName);
                foundUser.setDoctorAssigned(doctor.getDoctorId());

                doctor.setInAnEmergency("true");
                doctor.setIsAvailable("false");
                doctor.setUserEntity(foundUser);

//                us.updateUser(userName, foundUser);
                userRepository.save(foundUser);

                return doctorService.updateDoctor(doctor.getUserName(), doctor);
            }
        }

        //FIXME multiple dto is returned

        DoctorDto randomDoctorInDept = doctorsInDeptNotInEmer.get(rand.nextInt(doctorsInDeptNotInEmer.size()));

        UserEntity foundUser = userRepository.findByUserName(userName);
        foundUser.setDoctorAssigned(randomDoctorInDept.getDoctorId());

        randomDoctorInDept.setIsAvailable("false");
        randomDoctorInDept.setInAnEmergency("true");
        randomDoctorInDept.setUserEntity(foundUser);

//        userService.updateUser(userName, foundUser);
        userRepository.save(foundUser);

        return doctorService.updateDoctor(randomDoctorInDept.getUserName(), randomDoctorInDept);

    }
//    @Transactional
    public List<NurseDto> getNurseForDay(String userName){
        UserEntity userEntity = userRepository.findByUserName(userName);
        List<NurseDto> allNurses = new ArrayList<>();

        if (userEntity.getDayShiftNurseAssigned() != null && userEntity.getNightShiftNurseAssigned() != null) {
            allNurses.add(nurseService.getNurseById(userEntity.getDayShiftNurseAssigned()));
            allNurses.add(nurseService.getNurseById(userEntity.getNightShiftNurseAssigned()));

            return allNurses;
        }

//        ArrayList<String> days = new ArrayList<>(List.of(new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"}));
//        HashMap<String, String> hashMap = new HashMap<>();
//
//        for (String day : days) {
//            hashMap.put(day, "");
//        }

//        for (String day : days) {
//            List<Long> dayShiftNurses = nurseService.getAllDayShiftNurses(day);
//            List<Long> nightShiftNurses = nurseService.getAllNightShiftNurses(day);
//
//
//
//        }

        String day = LocalDate.now().getDayOfWeek().name();

        List<NurseDto> dayShiftNurses = nurseService.getAllDayShiftNurses(day);
        List<NurseDto> nightShiftNurses = nurseService.getAllNightShiftNurses(day);

        for (NurseDto dayNurse : dayShiftNurses) {
            if (dayNurse.getUserEntity() == null) {
                userEntity.setDayShiftNurseAssigned(dayNurse.getNurseId());
                dayNurse.setUserEntity(userEntity);
                allNurses.add(nurseService.setPatientInfo(dayNurse.getUserName(), dayNurse));
                break;
            }
        }

//        // Check if the userEntity is already attached
//        if (!entityManager.contains(userEntity)) {
//            userEntity = entityManager.merge(userEntity); // Reattach if necessary
//        }


        for (NurseDto nightNurse : nightShiftNurses) {
            if (nightNurse.getUserEntity() == null) {
                userEntity.setNightShiftNurseAssigned(nightNurse.getNurseId());
                nightNurse.setUserEntity(userEntity);
                allNurses.add(nurseService.setPatientInfo(nightNurse.getUserName(), nightNurse));
                break;
            }
        }

        userRepository.save(userEntity);

        return allNurses;
    }

}
