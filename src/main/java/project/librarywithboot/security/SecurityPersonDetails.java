package project.librarywithboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.librarywithboot.models.SecurityPerson;

import java.util.Collection;

public class SecurityPersonDetails implements UserDetails {

    private final SecurityPerson person;

    @Autowired
    public SecurityPersonDetails(SecurityPerson person) {
        this.person = person;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public SecurityPerson getPerson() {
        return person;
    }
}
