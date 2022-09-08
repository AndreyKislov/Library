package project.librarywithboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.librarywithboot.config.SecurityConf;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.repositories.SecurityRepositories;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final SecurityRepositories securityRepositories;
    private final SecurityConf securityConf;

    @Autowired
    public RegistrationService(SecurityRepositories securityRepositories, SecurityConf securityConf) {
        this.securityRepositories = securityRepositories;
        this.securityConf = securityConf;
    }

    public Optional<SecurityPerson> findByName(String s) {
        return securityRepositories.findPersonByUsername(s);
    }

    @Transactional
    public void save(SecurityPerson person) {
        person.setPassword(securityConf.getPasswordEncoder().encode(person.getPassword()));
        person.setRole("ROLE_USER");
        securityRepositories.save(person);
    }
}
