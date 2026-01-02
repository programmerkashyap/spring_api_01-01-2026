package com.example.crud_old.controller;

import com.example.crud_old.models.Contacts;
import com.example.crud_old.services.ContactsServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ContactsController {

    public ContactsServices contactsServices;
    public ContactsController(ContactsServices contactsServices)
    {
        this.contactsServices = contactsServices;
    }

    @PostMapping("/contacts")
    public Contacts save(@RequestBody Contacts data)
    {
        return contactsServices.save(data);
    }

    @GetMapping("/contacts")
    public List<Contacts> getall()
    {
        return  contactsServices.getAll();
    }

    @GetMapping("/contacts/{id}")
    public Contacts getone(@PathVariable Long id)
    {
        return contactsServices.getSingleData(id);
    }

    @DeleteMapping("/contacts/{id}")
    public String delete(@PathVariable Long id)
    {
        if(contactsServices.delete(id))
        {
            return "Data Deleted";
        }
        else
        {
            return "Unable to Delete";
        }
    }

    @PutMapping("/contacts")
    public Contacts update(@RequestBody Contacts data)
    {
        return contactsServices.update(data);
    }


}
