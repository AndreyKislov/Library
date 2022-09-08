package project.librarywithboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.librarywithboot.models.Book;
import project.librarywithboot.repositories.BookRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllOrderBy() {
        return bookRepository.OrderByBookName();
    }

    public Book findBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Optional<Book> findBook(String name) {
        return bookRepository.findByBookName(name);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void save(Book book) {
        book.setDateOfCreate(new Date());
        bookRepository.save(book);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        save(updateBook);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBook(String bookName) {
        return bookRepository.findByBookNameLikeIgnoreCaseOrderByBookName(bookName + "%%");
    }

    public List<Book> findBooksByDateOfAppointment(Date date) {
       return bookRepository.findBooksByDateOfAppointmentBeforeOrderByBookName(date);
    }
}
