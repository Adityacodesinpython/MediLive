package com.pharma_manager.MediLive.mapper;

import com.pharma_manager.MediLive.dto.AdminDto;
import com.pharma_manager.MediLive.entity.AdminEntity;

public class AdminMapper {
    // static as object creation isn't required
    public static AdminDto mapToAdminDto(AdminEntity adminEntity) {
        return new AdminDto(
                adminEntity.getAdminId(),
                adminEntity.getUserName(),
                adminEntity.getPassWord(),
                adminEntity.getRoles(),
                adminEntity.getProfileImageId()
        );
    }

    public static AdminEntity mapToAdminEntity(AdminDto adminDto) {
        return new AdminEntity(
                adminDto.getAdminId(),
                adminDto.getUserName(),
                adminDto.getPassWord(),
                adminDto.getRoles(),
                adminDto.getProfileImageId()
        );
    }
}
