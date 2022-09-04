package project.librarywithboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.librarywithboot.models.Book;
import project.librarywithboot.models.Reader;
import project.librarywithboot.repositories.ReaderRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final BookService bookService;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, BookService bookService) {
        this.readerRepository = readerRepository;
        this.bookService = bookService;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public List<Reader> findAll(Pageable pageable){
        return readerRepository.findAll(pageable).getContent();
    }

    public Reader findReader(int id) {
        return readerRepository.findReaderById(id);
    }

    public Optional<Reader> findReader(String email) {
        return readerRepository.findByEmail(email);
    }

    @Transactional
    public void save(Reader reader) {
        reader.setDateOfCreate(new Date());
        readerRepository.save(reader);
    }

    @Transactional
    public void update(int id, Reader updateReader) {
        Reader reader = findReader(id);
        reader.setName(updateReader.getName());
        reader.setDateOfBirthday(updateReader.getDateOfBirthday());
        reader.setEmail(updateReader.getEmail());
    }

    @Transactional
    public void delete(int id) {
        readerRepository.deleteById(id);
    }

    @Transactional
    public void addBookForReader(Reader reader, int bookId) {
        Book book = bookService.findBook(bookId);
        book.setDateOfAppointment(new Date());
        Reader readerBd = findReader(reader.getId());
        readerBd.setList(Collections.singletonList(book));
        book.setOwner(readerBd);
    }

    @Transactional
    public void removeBookForReader(int id) {
        Book book = bookService.findBook(id);
        book.setDateOfAppointment(null);
        book.setOwner(null);
    }

    public List<Reader> searchReader(String name) {
        return readerRepository.findByNameLikeIgnoreCaseOrderByName(name + "%%");
    }

    public List<Reader> overdueReader() {
        LocalDate localDate = LocalDate.now().minusDays(14);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Book> overdueBook = bookService.findBooksByDateOfAppointment(date);
        Set<Reader> set = new LinkedHashSet<>();
        for (Book book : overdueBook)
            set.add(book.getOwner());
        return new ArrayList<>(set);
    }

    public Reader findReaderById(int id){
        return readerRepository.findReaderById(id);
    }
}

