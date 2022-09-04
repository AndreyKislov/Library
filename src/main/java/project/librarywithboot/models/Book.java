package project.librarywithboot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "booklibrary")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Название не может быть короче 2х и длинее 30ти символов")
    private String bookName;

    @Column(name = "author")
    @Size(min = 2, max = 30, message = "Имя не может быть короче 2х и длинее 30ти символов")
    private String author;

    @Column(name = "year_of_writing")
    @Max(value = 2022, message = "Книга не может быть написана позде 2022 года")
    @Min(value = 0, message = "В нашей библиотеке нет книг написанных до н.э.")
    private int yearOfWriting;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreate;

    @Column(name = "date_of_appointment")
    @Temporal(TemporalType.DATE)
    private Date dateOfAppointment;

    @Transient
    private int overdue;

    @ManyToOne
    @JoinColumn(name = "id_readerlibrary")
    private Reader owner;

}