package com.pharma_manager.MediLive.config;


import com.pharma_manager.MediLive.entity.AdminEntity;
import com.pharma_manager.MediLive.entity.DoctorEntity;
import com.pharma_manager.MediLive.entity.NurseEntity;
import com.pharma_manager.MediLive.entity.UserEntity;
import com.pharma_manager.MediLive.repository.AdminRepository;
import com.pharma_manager.MediLive.repository.DoctorRepository;
import com.pharma_manager.MediLive.repository.NurseRepository;
import com.pharma_manager.MediLive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


// UserDetailsService class implementation for authentication using spring boot security
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(userName);

        if (userEntity != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.getUserName())
                    .password(userEntity.getPassWord())
                    .roles(userEntity.getRoles())
//                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();

        }

        AdminEntity adminEntity = adminRepository.findByUserName(userName);

        if (adminEntity != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(adminEntity.getUserName())
                    .password(adminEntity.getPassWord())
                    .roles(adminEntity.getRoles())
//                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();

        }

        DoctorEntity doctorEntity = doctorRepository.findByUserName(userName);

        if (doctorEntity != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(doctorEntity.getUserName())
                    .password(doctorEntity.getPassWord())
                    .roles(doctorEntity.getRoles())
//                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();

        }

        NurseEntity nurseEntity = nurseRepository.findByUserName(userName);

        if (nurseEntity != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(nurseEntity.getUserName())
                    .password(nurseEntity.getPassWord())
                    .roles(nurseEntity.getRoles())
//                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();

        }

        throw new UsernameNotFoundException("User not found with the username: " + userName);
    }

}
