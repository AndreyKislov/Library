package project.librarywithboot.services.securityServices;

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

    public Optional<SecurityPerson> findByUserName(String username){
        return securityRepositories.findByUsername(username);
    }

    @Transactional
    public void save(SecurityPerson securityPerson){
        securityRepositories.save(securityPerson);
    }

}
