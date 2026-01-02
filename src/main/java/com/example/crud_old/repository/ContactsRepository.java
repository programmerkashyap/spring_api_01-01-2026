package com.example.crud_old.repository;

import com.example.crud_old.models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {

}
