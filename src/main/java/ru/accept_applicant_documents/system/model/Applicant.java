package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "applicants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    private String firstname;
    private String surname;
    private String patronymic;

    private LocalDate dateOfBirth;

    private String pathToCharacteristicImage;

    @Column(unique = true)
    @NotNull
    private String phone;

}

/* API

SITE/ информация и работе сайта и тд.

SITE/register
SITE/login

SITE/applicant/lk
SITE/applicant/lk/edit
SITE/applicant/lk/edit/docs

SITE/applicant/lk/lists
SITE/applicant/lk/list/{id}
SITE/applicant/lk/groups
SITE/applicant/lk/group/{id}
SITE/applicant/lk/group/{id}/order



SITE/admin/lk/applicants/unverified     тута все абики, которые еще не прошли проверку
SITE/admin/lk/applicants/incorrect      тута все абики, у которых ошибками в отправленных доках
SITE/admin/lk/applicants/verified       доки подтверждены
SITE/admin/lk/applicant/{id}
SITE/admin/lk/applicant/{id}/docs
SITE/admin/lk/applicant/{id}/exams
SITE/admin/lk/applicant/{id}/orders
SITE/admin/lk/applicant/{id}/edit
SITE/admin/lk/applicant/{id}/edit/docs
SITE/admin/lk/applicant/{id}/edit/exams

SITE/admin/lk/personalFiles
SITE/admin/lk/personalFiles/{id}
SITE/admin/lk/personalFiles/{id}/edit
SITE/admin/lk/personalFiles/{id}/edit/docs
SITE/admin/lk/personalFiles/{id}/edit/scores

SITE/admin/lk/lists
SITE/admin/lk/groups


Логика

Вначале юзер попадает на главную страницу сайта с информацией о вузе и тд.
Далее переходит либо на вход, либо на регистрацию.

Вход:
    email
    password

Регистрация:
    email
    password
    номер телефона

Далее юзер обязан отправить фотографии документов на сайт через форму
 и дождаться ответа от сайта.

Если все успешно, то открывается доступ к спискам и тд.
Создается личное дело в БД.

*/
