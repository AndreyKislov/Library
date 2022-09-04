package project.librarywithboot.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.repositories.SecurityRepositories;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

  private final SecurityRepositories securityRepositories;

    public RegistrationService(SecurityRepositories securityRepositories) {
        this.securityRepositories = securityRepositories;
    }

    public Optional<SecurityPerson> findByName(String s){
       return securityRepositories.findPersonByUsername(s);
    }

    @Transactional
    public void save(SecurityPerson person){
        securityRepositories.save(person);
    }
}
