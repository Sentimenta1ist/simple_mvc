package com.app.RestAppTest.controllers;

import com.app.RestAppTest.dto.PersonDTO;
import com.app.RestAppTest.models.Person;
import com.app.RestAppTest.servises.PeopleService;
import com.app.RestAppTest.util.PersonErrorResponse;
import com.app.RestAppTest.util.PersonNotCreatedException;
import com.app.RestAppTest.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        // jackson convert this to json

        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorsMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorsMsg.toString());
        }

        peopleService.save(convertToPerson(personDTO));

        // send http response with empty body and status 200
        return ResponseEntity.ok(HttpStatus.OK);
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

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "person with this id not found!",
                System.currentTimeMillis()
        );

        // in http answer - body(response) and status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private Person convertToPerson(PersonDTO personDTO) {

        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

}
