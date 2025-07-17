package com.pharma_manager.MediLive.service;

import com.pharma_manager.MediLive.dto.AdminDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.AdminEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {
    //    AdminDto saveUser(AdminDto adminDto);
//
//    AdminDto findByUserName(String userName);
//
    List<UserDto> getAllUsers();
//
//    AdminDto getUserById(Long userId);

    AdminDto updateAdmin(String userName, AdminDto updateAdminInfo);

    void deleteByUserName(String userName);

    AdminDto createAdmin(AdminDto adminDto);

    AdminDto makeUserAdmin(String userName);

    byte[] downloadProfilePicture(String userName);

    void uploadProfilePicture(String userName, MultipartFile file);

    AdminEntity checkIfAdminExists(String userName);
}
