package project.librarywithboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.services.RegistrationService;

import java.util.Optional;


@Component
public class ValidatorForSecurityPerson implements Validator {

    private final RegistrationService service;

    public ValidatorForSecurityPerson(RegistrationService service) {
        this.service = service;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return SecurityPerson.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SecurityPerson person = (SecurityPerson) o;

        Optional<SecurityPerson> optional = service.findByName(person.getUsername());

        if(optional.isPresent())
            errors.rejectValue("name", "", "Пользователь с таким логином уже существует.");


    }
}
