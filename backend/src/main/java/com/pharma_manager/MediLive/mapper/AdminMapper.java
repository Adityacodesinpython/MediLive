package com.pharma_manager.MediLive.mapper;

import com.pharma_manager.MediLive.dto.AdminDto;
import com.pharma_manager.MediLive.entity.AdminEntity;

public class AdminMapper {
    // static as object creation isn't required
    public static AdminDto mapToAdminDto(AdminEntity userEntity) {
        return new AdminDto(
                userEntity.getAdminId(),
                userEntity.getUserName(),
                userEntity.getPassWord(),
                userEntity.getRoles()
        );
    }

    public static AdminEntity mapToAdminEntity(AdminDto userDto) {
        return new AdminEntity(
                userDto.getAdminId(),
                userDto.getUserName(),
                userDto.getPassWord(),
                userDto.getRoles()
        );
    }
}
