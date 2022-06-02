package com.example.FirstSpringSecurityApp.services;

import com.example.FirstSpringSecurityApp.models.Person;
import com.example.FirstSpringSecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void register(Person person){
        String encodePassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodePassword);

        peopleRepository.save(person);
    }
}
