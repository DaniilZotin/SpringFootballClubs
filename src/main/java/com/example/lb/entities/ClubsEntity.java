package com.example.lb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;



@Entity(name = "football_clubs")
@Component
public class ClubsEntity {


    @Id
    private int id;

    @Column(name = "name_club")
    private String name;
    private String county;


    private LocalDate year;

    private int rating;

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public ClubsEntity(Integer id,String name, String county, LocalDate year, int rating) {
        this.name = name;
        this.county = county;
        this.rating = rating;
        this.year = year;
        this.id = id;
    }

    public ClubsEntity(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }





}
