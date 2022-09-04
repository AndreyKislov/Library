package project.librarywithboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.librarywithboot.models.Reader;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    Optional<Reader> findByEmail(String email);

    List<Reader> findByNameLikeIgnoreCaseOrderByName(String name);

    @Query(value = "select p from Reader p left join fetch p.list where p.id = ?1")
    Reader findReaderById(int id);
}
