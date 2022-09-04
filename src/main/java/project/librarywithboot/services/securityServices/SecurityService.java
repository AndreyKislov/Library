package project.librarywithboot.services.securityServices;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.librarywithboot.models.SecurityPerson;
import project.librarywithboot.repositories.SecurityRepositories;
import security.SecurityPersonDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SecurityService implements UserDetailsService {

    private final SecurityRepositories securityRepositories;

    public SecurityService(SecurityRepositories securityRepositories) {
        this.securityRepositories = securityRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityPerson> optPerson = securityRepositories.findByUsername(username);

        if(optPerson.isEmpty()){
            throw new  UsernameNotFoundException("Пользователь не найден");
        }
        return new SecurityPersonDetails(optPerson.get());
    }
}
