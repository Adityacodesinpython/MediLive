package com.pharma_manager.MediLive.mapper;

import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.entity.NurseEntity;

public class NurseMapper {
    // static as object creation isn't required
    public static NurseDto mapToNurseDto(NurseEntity nurseEntity) {
        return new NurseDto(
                nurseEntity.getNurseId(),
                nurseEntity.getUserName(),
                nurseEntity.getPassWord(),
                nurseEntity.getDayShifts(),
                nurseEntity.getNightShifts(),
                nurseEntity.getUserEntity(),
                nurseEntity.getRoles()
        );
    }

    public static NurseEntity mapToNurseEntity(NurseDto nurseDto) {
        return new NurseEntity(
                nurseDto.getNurseId(),
                nurseDto.getUserName(),
                nurseDto.getPassWord(),
                nurseDto.getDayShifts(),
                nurseDto.getNightShifts(),
                nurseDto.getUserEntity(),
                nurseDto.getRoles()
        );
    }
}
