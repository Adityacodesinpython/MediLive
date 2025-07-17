package com.pharma_manager.MediLive.service.impl;

import com.pharma_manager.MediLive.aws.s3.S3Buckets;
import com.pharma_manager.MediLive.dto.AdminDto;
import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.AdminEntity;
import com.pharma_manager.MediLive.entity.UserEntity;
import com.pharma_manager.MediLive.exception.ResourceNotFoundException;
import com.pharma_manager.MediLive.mapper.AdminMapper;
import com.pharma_manager.MediLive.mapper.UserMapper;
import com.pharma_manager.MediLive.repository.AdminRepository;
import com.pharma_manager.MediLive.repository.UserRepository;
import com.pharma_manager.MediLive.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service    // creates a bean/instance of the service to use
@AllArgsConstructor     // construtor injection
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RedisServiceImpl redisService;
    private final S3Client s3Client;
    private final S3Buckets s3Buckets;


    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> cachedUsersEntity = redisService.getValue("admin_all_users", UserEntity.class);

        if (cachedUsersEntity != null) {
            return cachedUsersEntity.stream().map((oneUserEntity) -> UserMapper.mapToUserDto(oneUserEntity))
                    .collect(Collectors.toList());
        }
        List<UserEntity> allUsersEntity = userRepository.findAll();
        redisService.setValue("admin_all_users", allUsersEntity, 300L);    // 5 minutes expiry

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

    @Override
    public byte[] downloadProfilePicture(String userName) {

        String profileImageId = checkIfAdminExists(userName).getProfileImageId();

        if (profileImageId.isBlank()) {
            throw new ResourceNotFoundException("There is no profile picture for admin with the ID: " + userName);
        }

        var response = s3Client.getObjectAsBytes(
                builder -> builder.bucket(s3Buckets.getAdminBucket()).key("admin/" + userName + "/" + profileImageId + ".jpg").build()
        );
        return response.asByteArray();
    }

    @Transactional
    @Override
    public void uploadProfilePicture(String userName, MultipartFile file) {
        AdminEntity adminEntity = checkIfAdminExists(userName);
        String profileImageId = UUID.randomUUID().toString();

        try {
            s3Client.putObject(
                    builder -> builder.bucket(s3Buckets.getAdminBucket()).key("admin/" + userName + "/" + profileImageId + ".jpg").build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture for admin", e);
        }

        adminEntity.setProfileImageId(profileImageId); // Set the ID
        adminRepository.save(adminEntity);
    }

    @Override
    public AdminEntity checkIfAdminExists(String userName) throws ResourceNotFoundException{
        AdminEntity adminEntity = adminRepository.findByUserName(userName);

        if (adminEntity == null) {
            throw new ResourceNotFoundException("There is no admin with the ID: " + userName);
        }

        return adminEntity;
    }
}
