package project.librarywithboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.librarywithboot.models.Book;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

     Optional<Book> findByBookName(String name);

     @Query(value = "select b from Book b order by b.bookName")
     List<Book> OrderByBookName();

     List<Book> findByBookNameLikeIgnoreCaseOrderByBookName(String name);

     List<Book> findBooksByDateOfAppointmentBeforeOrderByBookName(Date date);
}
