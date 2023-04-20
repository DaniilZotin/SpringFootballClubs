package com.example.lb.controllers;

import com.example.lb.entities.ClubsEntity;
import com.example.lb.entities.UserEntity;
import com.example.lb.services.ServiceEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    ServiceEntity serviceEntity;

    @GetMapping("/log_in_system")
    public String index(Model model){
        model.addAttribute("user", new UserEntity());
        return "log";
    }


    @RequestMapping(value = "/log_in",
            method = RequestMethod.POST)
    public String getLog(@ModelAttribute("user") UserEntity userEntity, Model model){
        try {
            if (serviceEntity.isUserExist(userEntity.getUsername(),userEntity.getPassword())){
                System.out.println("yes");
                createModels(model);
                return "table";
            } else {
                model.addAttribute("message","Bad password or login");
                System.out.println("no");
                return "log";
            }
        } catch (NullPointerException e){
            System.out.println("error");
            model.addAttribute("message","Bad password or login");
            return "log";
        }
    }


    @RequestMapping(value = "/clubs_sample",
            method = RequestMethod.POST)
    public String getSample(@ModelAttribute("samples") ClubsEntity clubsEntity, Model model){
        model.addAttribute("number","Sum: " + serviceEntity.sumOfSampleClubs(serviceEntity.clubsSample(clubsEntity.getCounty())));
        model.addAttribute("club_delete", new ClubsEntity());
        model.addAttribute("clubs",serviceEntity.clubsSample(clubsEntity.getCounty()));
        return "table";
    }

    @RequestMapping(value = "/delete_club",
            method = RequestMethod.POST)
    public String deleteClub(@ModelAttribute("club_delete") ClubsEntity clubsEntity, Model model){
        createModels(model);
        System.out.println(clubsEntity.getCounty());
        serviceEntity.deleteByName(clubsEntity.getName());
        return "redirect:table";
    }

    @RequestMapping(value = "/create_club",
            method = RequestMethod.POST)
    public String createClub(@ModelAttribute("club_create") ClubsEntity clubsEntity, Model model){
        createModels(model);
        serviceEntity.saveClub(clubsEntity);
        return "redirect:table";
    }


    public void createModels(Model model){
        model.addAttribute("clubs", serviceEntity.allClubs());
        model.addAttribute("samples", new ClubsEntity());
        model.addAttribute("club_delete", new ClubsEntity());
        model.addAttribute("club_create", new ClubsEntity());
        model.addAttribute("club_refresh", new ClubsEntity());
        model.addAttribute("data_update", new ClubsEntity());
    }

    @GetMapping("/create_club")
    public String createClub(Model model){
        model.addAttribute("club_create", new ClubsEntity());
        return "create_table";
    }

    @GetMapping(value = "table")
    public String pageTable(Model model){
        createModels(model);
        return "table";
    }

    /*Коли ми натискаємо на кнопку "Оновити" в таблицях і ми виконуємо цей ендпоінт,
    після чого ми отримуємо країну по ній знаходимо усю сутність і запихуємо її в модель для відображення
    в полях на редагуванні, після чого створюємо нову сутність, зберігаємо id і видаляємо стару сутність
    переносимо на refresh і там уже уводяться усі нові дані для нової(оновленої) сутності, POST ставиться id яке
    ми зберегли й зберігається сутність
    */


    @GetMapping("/refresh_club")
    public String refreshClub(@ModelAttribute("club_refresh") ClubsEntity clubsEntity,Model model){
        ClubsEntity clubForRefresh = serviceEntity.findByName(clubsEntity.getName());
        model.addAttribute("club_for_refresh", clubForRefresh);
        model.addAttribute("data_update", new ClubsEntity());
        if(serviceEntity.rememberIdAndDeleteClub(clubForRefresh,clubsEntity.getName())){
            serviceEntity.rememberIdAndDeleteClub(clubForRefresh,clubsEntity.getName());
            return "refresh";
        } else {
            return "redirect:table";
        }

    }

    @RequestMapping(value = "/updated_data",
            method = RequestMethod.POST)
    public String dataForFinalUpdate(@ModelAttribute("data_update") ClubsEntity clubsEntity,Model model){
        clubsEntity.setId(serviceEntity.idForDelete);
        serviceEntity.saveClub(clubsEntity);
        return "redirect:table";
    }








}
