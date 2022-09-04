package project.librarywithboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.services.securityServices.RegistrationService;

import java.util.Optional;

public class ValidForSecurityPerson implements Validator {

    private final RegistrationService service;

    @Autowired
    public ValidForSecurityPerson(RegistrationService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SecurityPerson.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SecurityPerson securityPerson = (SecurityPerson) target;

        Optional<SecurityPerson> optPerson = service.findByUserName((securityPerson.getUsername()));

        if (optPerson.isPresent()){
            errors.rejectValue("username", "", "Пользоватль с таким логином уже существует");
        }
    }
}
