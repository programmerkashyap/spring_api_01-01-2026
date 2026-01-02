package com.example.crud_old.services;

import com.example.crud_old.models.Contacts;
import com.example.crud_old.repository.ContactsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServices {

    public ContactsRepository contactsRepository;

    public ContactsServices(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    public Contacts save(Contacts data) {
        return contactsRepository.save(data);
    }

    public List<Contacts> getAll() {
        return contactsRepository.findAll();
    }

    public Contacts getSingleData(Long id) {
        return contactsRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        Contacts data = contactsRepository.findById(id).orElse(null);
        if (data != null) {
            contactsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Contacts update(Contacts data)
    {
        if(data.getId()!=null)
        {
            return contactsRepository.save(data);
        }
        else
        {
            return null;
        }
    }

}
