package com.example.lb.services;

import com.example.lb.entities.ClubsEntity;
import com.example.lb.entities.UserEntity;
import com.example.lb.repositories.IClubsRepository;
import com.example.lb.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServiceEntity {

    @Autowired
    IClubsRepository iClubsRepository;

    @Autowired
    IUserRepository iUserRepository;


    public Boolean isUserExist(String name, String password){
        System.out.println(name + " " + password);
        System.out.println(password);
        List<UserEntity> entityList = (List<UserEntity>) iUserRepository.findAll();
        UserEntity userEntity = entityList.stream().filter(userE -> userE.getUsername().equals(name)).findAny().orElse(null);
        if(userEntity.getPassword().equals(password)){
            return true;
        } else {
            return false;
        }
    }

    public List<ClubsEntity> allClubs(){
        return (List<ClubsEntity>) iClubsRepository.findAll();
    }

    public List<ClubsEntity> clubsSample(String nameClub){


        List<ClubsEntity> clubsEntities = (List<ClubsEntity>) iClubsRepository.findAll();
        List<ClubsEntity> resultClubsSample = clubsEntities.stream()
                .filter(clubsEntity -> clubsEntity.getCounty().equals(nameClub))
                .collect(Collectors.toList());
        System.out.println(resultClubsSample);

        return resultClubsSample;
    }

    public Integer sum = 0;
    public Integer sumOfSampleClubs(List<ClubsEntity> listOfSampleClubs){
        sum = 0;
        listOfSampleClubs.forEach(el->{
            sum += el.getRating();
        });
        System.out.println(sum);
        return sum;
    }

    public void deleteByName(String clubName){
        ClubsEntity clubsEntity = iClubsRepository.findByName(clubName);
        if(clubsEntity == null){
            System.out.println("You did not chose");
        } else {
            iClubsRepository.deleteById((long) clubsEntity.getId());
        }
    }

    public void saveClub(ClubsEntity clubsEntity){
        System.out.println("i am here");
        if(clubsEntity.getYear()!= null && !clubsEntity.getCounty().trim().isEmpty()
                && !clubsEntity.getName().trim().isEmpty()){
            iClubsRepository.save(clubsEntity);
            System.out.println(clubsEntity.getCounty());
        } else {
            System.out.println("Input info please");
        }
    }

    public ClubsEntity findByName(String nameClub){
        return iClubsRepository.findByName(nameClub);
    }

    public ClubsEntity updateEntity(String county, String name){

        ClubsEntity readyToDelete = iClubsRepository.findByCountyAndName(county,name);
        System.out.println(readyToDelete.getCounty());
        return null;
    }

    public static int idForDelete;
    public boolean rememberIdAndDeleteClub(ClubsEntity clubsEntity, String club){
        try{
            //перевіряємо чи прийшло імя клубу бо може прийти і не воно а якась діч
            List<ClubsEntity> allClubsByName = iClubsRepository.findAllByName(club);
            //якщо клубів по такому імені не знайдено, значить прийшло не правильне імя клубу або взагалі не імя клубу
            if(allClubsByName.isEmpty()){
                System.out.println("null");
                return false;
            }
            idForDelete = clubsEntity.getId();
            System.out.println(idForDelete + " id for delete");
            iClubsRepository.deleteById((long) idForDelete);
            return true;
        } catch (NullPointerException e){
            System.out.println("Nice");
            return false;
        }

    }





}
