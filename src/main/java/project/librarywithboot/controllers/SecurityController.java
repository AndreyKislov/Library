package project.librarywithboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.services.RegistrationService;
import project.librarywithboot.validator.ValidatorForSecurityPerson;


import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class SecurityController {

    private final RegistrationService service;
    private final ValidatorForSecurityPerson validatorForPerson;

    @Autowired
    public SecurityController(RegistrationService service, ValidatorForSecurityPerson validatorForPerson) {
        this.service = service;
        this.validatorForPerson = validatorForPerson;
    }

    @GetMapping("/login")
    public String customLogin(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") SecurityPerson person){

        return "/auth/reg";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute ("person") @Valid SecurityPerson person,
                                      BindingResult bindingResult){
        validatorForPerson.validate(person, bindingResult);
       if(bindingResult.hasErrors())
           return "/auth/reg";
        service.save(person);
        return "redirect:/auth/login";
    }
}
