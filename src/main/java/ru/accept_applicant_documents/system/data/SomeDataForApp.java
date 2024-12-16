package ru.accept_applicant_documents.system.data;

import ru.accept_applicant_documents.system.model.Applicant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SomeDataForApp {

    List<Applicant> createApplicants() {

        List<Applicant> applicants = new ArrayList<>();

        applicants.add((new Applicant(null, "nersus1337@mail.ru", "secret-key",
                "Максим", "Прямоносов", "Олегович",
                LocalDate.of(2003, 4, 22), "C:/photo/1.jpg","89831719155", "Непроверены")));

        applicants.add((new Applicant(null, "mtay@mail.ru", "secret-key213",
                "Михаил", "Тайчер", "Олегович",
                LocalDate.of(2003, 8, 21), "C:/photo/2.jpg","89831719155", "Непроверены")));

        applicants.add((new Applicant(null, "nersus1337@mail.ru", "secret-key",
                "Иван", "Чепрасов", "Олегович",
                LocalDate.of(2003, 1, 9), "C:/photo/3.jpg","89831719155", "Непроверены")));

        applicants.add((new Applicant(null, "nersus1337@mail.ru", "secret-key",
                "Дарья", "Функ", "Олеговна",
                LocalDate.of(2003, 2, 2), "C:/photo/4.jpg","89831719155", "Непроверены")));

        return applicants;
    }

}
