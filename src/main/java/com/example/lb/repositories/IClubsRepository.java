package com.example.lb.repositories;

import com.example.lb.entities.ClubsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClubsRepository extends JpaRepository<ClubsEntity, Long> {
    List<ClubsEntity> findAllByName(String name);

    long deleteByName(String name);

    long removeByRating(Integer rating);

    ClubsEntity findByName(String name);

    ClubsEntity findByCountyAndName(String county, String name);

    List<ClubsEntity> findAllByCounty(String county);

}
