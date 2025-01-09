package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.accept_applicant_documents.system.enums.Roles;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusesOfDocuments docStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<ExamResult> examResult;

}

/* API

SITE/ информация и работе сайта и тд.

SITE/register
SITE/login

SITE/applicant/lk - личный кабинет, инф об абитуриенте
SITE/applicant/lk/edit - редактирование инф об абит
SITE/applicant/lk/edit/docs - загрузка документов абитуриента,статус документов

SITE/applicant/lk/lists - все списки зачисления
SITE/applicant/lk/list/{id} - посмотреть конкретный список

SITE/applicant/lk/groups - конкурсная группа
SITE/applicant/lk/group/{id}
SITE/applicant/lk/group/{id}/order - выбор приоритетной конкурсной группы

SITE/admin/lk/applicants/unverified тута все абики, которые еще не прошли проверку
SITE/admin/lk/applicants/unverified/{id}
SITE/admin/lk/applicants/incorrect тута все абики, у которых ошибками в отправленных доках
SITE/admin/lk/applicants/incorrect/{id}
SITE/admin/lk/applicants/verified доки подтверждены
SITE/admin/lk/applicants/verified/{id}
SITE/admin/lk/applicant/{id} - инф об конкретном абитуриенте
SITE/admin/lk/applicant/{id}/docs - документы конкретного абитуриента
SITE/admin/lk/applicant/{id}/exams - все экзамены абитуриента
SITE/admin/lk/applicant/{id}/orders - просмотр выбранной конкурсной группы
SITE/admin/lk/applicant/{id}/edit - заполнение данных абитуриента
SITE/admin/lk/applicant/{id}/edit/docs - внесение данных документов в текстовый вид
SITE/admin/lk/applicant/{id}/edit/exams - заполнение данных об экзаменах

SITE/admin/lk/personalFiles - просмотр личных дел
SITE/admin/lk/personalFiles/{id} - конкретное дело
SITE/admin/lk/personalFiles/{id}/edit - редактирование личного дела
SITE/admin/lk/personalFiles/{id}/edit/docs - внесение данных документов в текстовый вид
SITE/admin/lk/personalFiles/{id}/edit/scores - внесение доплнительных баллов

SITE/admin/lk/lists - списки зачисления
SITE/admin/lk/lists/{id} - конкретный список зачисления
SITE/admin/lk/groups - конкурсные группы
SITE/admin/lk/groups/{id} - конкретный конкурсная группа

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

Непроверено
Подлинно
Неправильно - при новой подаче переходит в Непроверено

// Тригеры, процедуры, функции SQL

    1. При подаче заявки на поступление, список автоматически обновляется и добавляет этого абитуриента.

    2. Автоматическое создание личного дела при установлении подлинности документов

    3. Ограничение от 0 до 100 для результатов экзаменов.

    4. Ограничение на количество заявок (5)

    5. Ограничение на 1 приоритетное направление

    6. Ограничение на просроченные результаты экзаменов (4 года)

    7. Если удаляется applicant, то также удаляются все связанные с ним данные:

    8. Функция сортировки списков

*/
