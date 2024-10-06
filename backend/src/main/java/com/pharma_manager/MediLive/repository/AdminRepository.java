package com.pharma_manager.MediLive.repository;

import com.pharma_manager.MediLive.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    // declare all the functions that are not in jpa repo
    AdminEntity findByUserName(String userName);

    void deleteByUserName(String userName);
}
