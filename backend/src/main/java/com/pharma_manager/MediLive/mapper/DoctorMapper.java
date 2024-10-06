package com.pharma_manager.MediLive.mapper;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.entity.DoctorEntity;

public class DoctorMapper {
    // static as object creation isn't required
    public static DoctorDto mapToDoctorDto(DoctorEntity doctorEntity) {
        return new DoctorDto(
                doctorEntity.getDoctorId(),
                doctorEntity.getUserName(),
                doctorEntity.getPassWord(),
                doctorEntity.getDepartment(),
                doctorEntity.getIsAvailable(),
                doctorEntity.getInAnEmergency(),
                doctorEntity.getUserEntity(),
                doctorEntity.getRoles()
        );
    }

    public static DoctorEntity mapToDoctorEntity(DoctorDto doctorDto) {
        return new DoctorEntity(
                doctorDto.getDoctorId(),
                doctorDto.getUserName(),
                doctorDto.getPassWord(),
                doctorDto.getDepartment(),
                doctorDto.getIsAvailable(),
                doctorDto.getInAnEmergency(),
                doctorDto.getUserEntity(),
                doctorDto.getRoles()
        );
    }
}
