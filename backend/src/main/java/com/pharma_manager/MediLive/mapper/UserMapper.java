package com.pharma_manager.MediLive.mapper;

import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.UserEntity;

public class UserMapper {
    // static as object creation isn't required
    public static UserDto mapToUserDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getPassWord(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getRoles(),
                userEntity.getDoctorAssigned(),
                userEntity.getDayShiftNurseAssigned(),
                userEntity.getNightShiftNurseAssigned()
        );
    }

    public static UserEntity mapToUserEntity(UserDto userDto) {
        return new UserEntity(
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getPassWord(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getRoles(),
                userDto.getDoctorAssigned(),
                userDto.getDayShiftNurseAssigned(),
                userDto.getNightShiftNurseAssigned()
        );
    }
}
