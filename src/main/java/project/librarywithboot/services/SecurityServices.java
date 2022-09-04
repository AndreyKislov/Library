package project.librarywithboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.repositories.SecurityRepositories;
import project.librarywithboot.security.SecurityPersonDetails;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class SecurityServices implements UserDetailsService {

    private final SecurityRepositories securityRepositories;

    @Autowired
    public SecurityServices(SecurityRepositories securityRepositories) {
        this.securityRepositories = securityRepositories;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityPerson> personOptional = securityRepositories.findPersonByUsername(username);

        if(personOptional.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new SecurityPersonDetails(personOptional.get());
    }
}
