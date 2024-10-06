package com.pharma_manager.MediLive.repository;

import com.pharma_manager.MediLive.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // declare all the functions that are not in jpa repo
    UserEntity findByUserName(String userName);

    void deleteByUserName(String userName);
}
