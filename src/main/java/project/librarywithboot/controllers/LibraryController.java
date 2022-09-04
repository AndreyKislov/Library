package project.librarywithboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.librarywithboot.models.Book;
import project.librarywithboot.models.Reader;
import project.librarywithboot.security.SecurityPersonDetails;
import project.librarywithboot.services.BookService;
import project.librarywithboot.services.ReaderService;
import project.librarywithboot.util.date.ByDate;
import project.librarywithboot.validator.ValidForBook;
import project.librarywithboot.validator.ValidForReader;
import javax.validation.Valid;
import java.util.List;


@Controller
public class LibraryController {
    private final BookService bookService;
    private final ReaderService readerService;
    private final ValidForBook validForBook;
    private final ValidForReader validForReader;
    private final ByDate byDate;

    @Autowired
    public LibraryController(BookService bookService, ReaderService readerService,
                             ValidForBook validForBook, ValidForReader validForReader, ByDate byDate) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.validForBook = validForBook;
        this.validForReader = validForReader;
        this.byDate = byDate;
    }

    @GetMapping("/main")
    public String mainTheme(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityPersonDetails personDetails = (SecurityPersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        return "main/main";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model, @ModelAttribute("book") Book book) {
        model.addAttribute("book", book);
        model.addAttribute("books", bookService.findAllOrderBy());
        return "books/getAllBooks";
    }

    @GetMapping("/books/search")
    public String searchBook(@ModelAttribute("book") Book book, Model model) {
        List<Book> list = bookService.searchBook(book.getBookName());
        model.addAttribute("size", list.size());
        model.addAttribute("books", list);
        return "books/foundBook";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable("id") int id, Model model,
                          @ModelAttribute("reader") Reader reader) {

        Book book = bookService.findBook(id);
        if (book.getDateOfAppointment() != null) {
            book.setOverdue(byDate.getDay(book.getDateOfAppointment()));
            if (book.getOverdue() > 14) {
                model.addAttribute("integer2", 1);
            }
        }
        model.addAttribute("book", book);
        if (book.getOwner() == null) {
            model.addAttribute("integer", 0);
            model.addAttribute("readers", readerService.findAll());
        } else {
            model.addAttribute("owner", book.getOwner());
            model.addAttribute("integer", 1);
        }
        return "books/getBook";
    }

    @GetMapping("books/new")
    public String getNewBook(@ModelAttribute("book") Book book) {

        return "books/newBook";
    }

    @PostMapping("/books")
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        validForBook.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/newBook";

        bookService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findBook(id));
        return "books/editBook";
    }

    @PatchMapping("/books/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        validForBook.validate(book, bindingResult, id);
        if (bindingResult.hasErrors())
            return "books/editBook";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @PostMapping("/books/{bookId}/select")
    public String addBooksForRead(@PathVariable("bookId") int bookId, @ModelAttribute("readers") Reader reader) {
        readerService.addBookForReader(reader, bookId);
        return "redirect:/books/{bookId}";
    }

    @PatchMapping("/books/{Id}/clear")
    public String removeBookForReader(@PathVariable("Id") int bookId) {
        readerService.removeBookForReader(bookId);
        return "redirect:/books/{Id}";
    }

    @GetMapping("/readers")
    public String getAllReader(@ModelAttribute("reader") Reader reader, Model model) {
        List<Reader> list = readerService.findAll();
        if (list.size() != 0)
            for (Reader read : list)
                read.setAge(byDate.getAge(read.getDateOfBirthday()));
        model.addAttribute("readers", list);
        return "readers/getAllReaders";
    }

    /*@GetMapping("/readers")
    public String getAllReader(@ModelAttribute("reader") Reader reader, Model model,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size){
        List<Reader> list = readerService
                .findAll(PageRequest.of(page, size, Sort.by("name")));
        if (list.size() != 0)
            for (Reader read : list)
                read.setAge(byDate.getAge(read.getDateOfBirthday()));
        model.addAttribute("readers", list);
        return "readers/getAllReaders";
    }*/


    @GetMapping("/readers/{id}")
    public String getReader(@PathVariable("id") int id, Model model) {
        Reader reader = readerService.findReaderById(id);
        reader.setAge(byDate.getAge(reader.getDateOfBirthday()));
        model.addAttribute("reader", reader);
        List<Book> list = reader.getList();
        int size = list.size();
        model.addAttribute("size", size);
        if (size != 0) {
            model.addAttribute("books", list);
        }
        return "readers/getReader";
    }

    @GetMapping("/readers/search")
    public String searchReader(@ModelAttribute("reader") Reader reader, Model model) {
        List<Reader> list = readerService.searchReader(reader.getName());
        model.addAttribute("size", list.size());
        model.addAttribute("readers", list);
        return "readers/foundReader";
    }

    @GetMapping("/readers/new")
    public String getNewReader(@ModelAttribute("reader") Reader reader) {

        return "readers/newReader";
    }

    @PostMapping("/readers")
    public String createReader(@ModelAttribute("reader") @Valid Reader reader,
                               BindingResult bindingResult) {
        validForReader.validate(reader, bindingResult);
        if (bindingResult.hasErrors())
            return "readers/newReader";

        readerService.save(reader);
        return "redirect:/readers";
    }

    @DeleteMapping("/readers/{id}")
    public String deleteReader(@PathVariable("id") int id) {
        readerService.delete(id);
        return "redirect:/readers";
    }

    @GetMapping("/readers/{id}/edit")
    public String editReader(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerService.findReader(id));
        return "readers/editReader";
    }

    @PatchMapping("/readers/{id}")
    public String updateReader(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        validForReader.validate(reader, bindingResult, id);
        if (bindingResult.hasErrors())
            return "readers/editReader";

        readerService.update(id, reader);
        return "redirect:/readers";
    }

    @GetMapping("readers/overdue")
    public String allOverdue(Model model) {
        List<Reader> list = readerService.overdueReader();
        if (list.size() != 0)
            for (Reader read : list)
                read.setAge(byDate.getAge(read.getDateOfBirthday()));
        model.addAttribute("readers", list);
        return "readers/allOverdueReaders";
    }

}
