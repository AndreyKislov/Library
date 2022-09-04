package project.librarywithboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.librarywithboot.models.Reader;
import project.librarywithboot.services.ReaderService;
import java.util.Optional;

@Component
public class ValidForReader implements Validator {

    private final ReaderService readerService;

    @Autowired
    public ValidForReader(ReaderService readerService) {
        this.readerService = readerService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Reader.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Reader reader = (Reader) o;

        Optional<Reader> optionalReader = readerService.findReader(reader.getEmail());
        if (optionalReader.isPresent())
            errors.rejectValue("email", "", "Пользователь с таким адресом почтового ящика уже существует");
    }

    public void validate(Object o, Errors errors, int id) {

        Reader updateReader = (Reader) o;

        Optional<Reader> optionalReader = readerService.findReader(updateReader.getEmail());
        Reader reader = readerService.findReader(id);
        Reader optReader;
        if (optionalReader.isPresent()) {
            optReader = optionalReader.get();

            if (optReader.getId() != reader.getId())
                errors.rejectValue("email", "", "Пользователь с таким адресом почтового ящика уже существует");
        }
    }
}
