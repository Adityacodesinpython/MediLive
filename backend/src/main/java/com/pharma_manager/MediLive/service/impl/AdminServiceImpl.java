package com.pharma_manager.MediLive.service.impl;

import com.pharma_manager.MediLive.dto.AdminDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.AdminEntity;
import com.pharma_manager.MediLive.entity.UserEntity;
import com.pharma_manager.MediLive.mapper.AdminMapper;
import com.pharma_manager.MediLive.mapper.UserMapper;
import com.pharma_manager.MediLive.repository.AdminRepository;
import com.pharma_manager.MediLive.repository.UserRepository;
import com.pharma_manager.MediLive.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service    // creates a bean/instance of the service to use
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> allUsersEntity = userRepository.findAll();
        return allUsersEntity.stream().map((oneUserEntity) -> UserMapper.mapToUserDto(oneUserEntity))
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto updateAdmin(String userName, AdminDto updateAdminInfo) {

        AdminEntity foundAdmin = adminRepository.findByUserName(userName);

        foundAdmin.setUserName(updateAdminInfo.getUserName() == null ? foundAdmin.getUserName() : updateAdminInfo.getUserName());
        foundAdmin.setPassWord(updateAdminInfo.getPassWord() == null ? foundAdmin.getPassWord() : passwordEncoder.encode(updateAdminInfo.getPassWord()));
        //FIXME in all set-roles, i am replacing roles instead of appending.
        foundAdmin.setRoles(updateAdminInfo.getRoles() == null ? foundAdmin.getRoles() : updateAdminInfo.getRoles());

        AdminEntity updatedAdmin = adminRepository.save(foundAdmin);
        return AdminMapper.mapToAdminDto(updatedAdmin);
    }
    @Transactional
    @Override
    public void deleteByUserName(String userName) {
        adminRepository.deleteByUserName(userName);
    }

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        AdminEntity adminEntity = AdminMapper.mapToAdminEntity(adminDto);

        adminEntity.setPassWord(passwordEncoder.encode(adminEntity.getPassWord()));
        adminEntity.setRoles(new String[]{"ADMIN"});

        adminRepository.save(adminEntity);

        return AdminMapper.mapToAdminDto(adminEntity);
    }

    @Override
    public AdminDto makeUserAdmin(String userName){
        UserEntity userEntity = userRepository.findByUserName(userName);

        AdminEntity newAdmin = new AdminEntity();

        newAdmin.setUserName(userEntity.getUserName());
        newAdmin.setPassWord(userEntity.getPassWord());

        String[] adminRoles = new String[userEntity.getRoles().length + 1];
        System.arraycopy(userEntity.getRoles(), 0, adminRoles, 0, userEntity.getRoles().length);
        adminRoles[adminRoles.length - 1] = "ADMIN";

        newAdmin.setRoles(adminRoles);
        userEntity.setRoles(adminRoles);

        adminRepository.save(newAdmin);
        userRepository.save(userEntity);

        return AdminMapper.mapToAdminDto(newAdmin);
    }
}
