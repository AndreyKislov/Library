package project.librarywithboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.librarywithboot.models.SecurityPerson;


import java.util.Optional;

@Repository
public interface SecurityRepositories extends JpaRepository<SecurityPerson, Integer> {

    Optional<SecurityPerson> findPersonByUsername(String name);


}
