package project.librarywithboot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "readerlibrary")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Имя не может быть короче 2х и длинее 30ти символов")
    private String name;

    @Column(name = "email")
    @Email(message = "Введите валидный адрес почтового ящика")
    @NotEmpty(message = "Поле не может быть пустым")
    private String email;

    @Column(name = "date_of_birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Дата рождения не может быть в будущем")
    private Date dateOfBirthday;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreate;

    @Transient
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> list;

}
