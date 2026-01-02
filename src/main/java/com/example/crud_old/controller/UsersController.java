package com.example.crud_old.controller;

import com.example.crud_old.models.Users;
import com.example.crud_old.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")

public class UsersController {

    private UsersRepository usersRepository;
    public UsersController(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    @PostMapping("/create")
    public Users Create(@RequestBody Users data)
    {
        return usersRepository.save(data);
    }

    @GetMapping("/showusers")
    public List<Users> getAll()
    {
        return usersRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public Users singleUser(@PathVariable Long id)
    {
        return usersRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        usersRepository.deleteById(id);
        return "User Deleted Successfully";
    }

    @PutMapping("update/{id}")
    public Users update(@PathVariable Long id, @RequestBody Users newdata)
    {
        Users olddata = usersRepository.findById(id).orElse(null);
        if(olddata!=null)
        {
            olddata.setName(newdata.getName());
            olddata.setEmail(newdata.getEmail());
            olddata.setPassword(newdata.getPassword());
            olddata.setMobile(newdata.getMobile());
            olddata.setCity(newdata.getCity());

            usersRepository.save(olddata);
        }

        return olddata;
    }



}

