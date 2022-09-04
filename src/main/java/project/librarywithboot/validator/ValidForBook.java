package project.librarywithboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.librarywithboot.models.Book;
import project.librarywithboot.services.BookService;

import java.util.Optional;

@Component
public class ValidForBook implements Validator {


    private final BookService bookService;

    @Autowired
    public ValidForBook(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Book book = (Book) o;

        Optional<Book> bookOptional = bookService.findBook(book.getBookName());
        if(bookOptional.isPresent())
            errors.rejectValue("bookName", "", "Книга с таким названием уже существует");
    }

    public void validate(Object o, Errors errors, int id){
        Book book = (Book) o;

        Optional<Book> bookOptional = bookService.findBook(book.getBookName());
        Book bookForId = bookService.findBook(book.getId());
        Book bookOpt;
        if(bookOptional.isPresent()) {
            bookOpt = bookOptional.get();

            if(bookForId.getId() != bookOpt.getId()){
                errors.rejectValue("bookName", "", "Книга с таким названием уже существует");
            }
        }

    }
}
