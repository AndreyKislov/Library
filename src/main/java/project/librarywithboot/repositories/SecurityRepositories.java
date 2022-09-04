package project.librarywithboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import project.librarywithboot.models.SecurityPerson;

import java.util.Optional;

public interface SecurityRepositories extends JpaRepository<SecurityPerson, Integer> {
  Optional<SecurityPerson> findByUsername(String username);
}
