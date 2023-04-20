package com.example.lb.repositories;

import com.example.lb.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findByUsername(String username);
}
