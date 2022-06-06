package com.app.RestAppTest.controllers;

import com.app.RestAppTest.models.Person;
import com.app.RestAppTest.servises.PeopleService;
import com.app.RestAppTest.util.PersonErrorResponse;
import com.app.RestAppTest.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> findAll() {
        return peopleService.findAll(); // jackson convert this to json
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "person with this id not found!",
                System.currentTimeMillis()
        );

        // in http answer - body(response) and status
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
