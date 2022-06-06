package com.app.RestAppTest.util;

import com.app.RestAppTest.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        /*if(personDAO.show(person.getEmail()).isPresent()){
            errors.rejectValue("email", "",  "This email already taken");
        }*/

    }
}
