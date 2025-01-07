package ru.accept_applicant_documents.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.accept_applicant_documents.system.enums.Roles;
import ru.accept_applicant_documents.system.model.*;
import ru.accept_applicant_documents.system.repository.*;
import ru.accept_applicant_documents.system.service.AdminService;
import ru.accept_applicant_documents.system.service.ApplicantService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SystemApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ApplicantService applicantService;
    @Autowired
    AdminService adminService;
    @Autowired
    ApplicantRepo applicantRepo;
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    CompetitionGroupRepo competitionGroupRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    FormOfEducationRepo formOfEducationRepo;
    @Autowired
    SubjectOfDepartmentRepo SubjectOfDepartmentRepo;

    @Override
    public void run(String... args) throws Exception {
        // Создание админа по умолчанию
        if (!adminService.AdminAccountAlreadyExist("admin@mail.ru")) {
            adminRepo.save(new Admin(null, "admin@mail.ru", "Адольф",
                    bCryptPasswordEncoder.encode("12345678"), Roles.ADMIN));
        }
        if (departmentRepo.findById(1L).isEmpty())
            init();
        if (subjectRepo.findById(1L).isEmpty())
            initallsubjects();
        if (categoryRepo.findById(1L).isEmpty())
            init3();
        if (formOfEducationRepo.findById(1L).isEmpty())
            init4();
		if (competitionGroupRepo.findById(1L).isEmpty())
			initallCompetitionGroups();
        if (SubjectOfDepartmentRepo.findById(1L).isEmpty())
            initSubjectOfDepartments();
    }

    public void init3() {
        List<Category> categories = Arrays.asList(
                new Category(null, "Бюджет"),
                new Category(null, "Платно"),
                new Category(null, "Целевое")
        );
        categoryRepo.saveAll(categories);
    }

    public void init4() {
        List<FormOfEducation> formOfEducations = Arrays.asList(
                new FormOfEducation(null, "Очно"),
                new FormOfEducation(null, "Заочно")
        );
        formOfEducationRepo.saveAll(formOfEducations);
    }

    public void init() {
        List<Department> departments = Arrays.asList(
                new Department(null, "Физико химическая биология", "Биологии", "01.01.01"),
                new Department(null, "Общая биология", "Биологии", "01.01.02"),
                new Department(null, "Экология и природопользование", "Биологии", "01.01.03"),
                new Department(null, "Теология", "Теологии", "02.01.01"),
                new Department(null, "Религиоведение", "Теологии", "02.01.02"),
                new Department(null, "Филология", "Филологии", "03.01.01"),
                new Department(null, "Социальная работа", "Социологии", "04.01.01"),
                new Department(null, "География", "Географии", "05.01.01"),
                new Department(null, "Картография и геоинформатика", "Географии", "05.01.02"),
                new Department(null, "Гидрометеорология", "Географии", "05.01.03"),
                new Department(null, "Туризм", "Географии", "05.01.04"),
                new Department(null, "Геология и полезные ископаемые", "Геологии", "06.01.01"),
                new Department(null, "Геофизика", "Геологии", "06.01.02"),
                new Department(null, "Геохимия", "Геологии", "06.01.03"),
                new Department(null, "Геология и геохимия полезных ископаемых", "Геологии", "06.01.04"),
                new Department(null, "Гидрогеология инженерная геология геокриология", "Геологии", "06.01.05"),
                new Department(null, "Экологическая геология", "Геологии", "06.01.06"),
                new Department(null, "Журналистика", "Журналистики", "07.01.01"),
                new Department(null, "Международная журналистика", "Журналистики", "07.01.02"),
                new Department(null, "Мультимедийная журналистика", "Журналистики", "07.01.03"),
                new Department(null, "Периодическая печать", "Журналистики", "07.01.04"),
                new Department(null, "Телевизионная журналистика", "Журналистики", "07.01.05"),
                new Department(null, "Интернет журналистика", "Журналистики", "07.01.06"),
                new Department(null, "Бизнес журналистика", "Журналистики", "07.01.07"),
                new Department(null, "Политическая журналистика", "Журналистики", "07.01.08"),
                new Department(null, "Информатика и вычислительная техника", "Информации", "08.01.01"),
                new Department(null, "Прикладная информатика", "Информации", "08.01.02"),
                new Department(null, "Программная инженерия", "Информации", "08.01.03"),
                new Department(null, "Информационная безопасность", "Информации", "08.01.04"),
                new Department(null, "Приборостроение", "Информации", "08.01.05"),
                new Department(null, "Инноватика", "Информации", "08.01.06"),
                new Department(null, "Бизнес информатика", "Информации", "08.01.07"),
                new Department(null, "История", "Истории", "09.01.01"),
                new Department(null, "Документоведение", "Истории", "09.01.02"),
                new Department(null, "Музееведение и охрана историко культурного наследия", "Истории", "09.01.03"),
                new Department(null, "Историко архивоведение", "Истории", "09.01.04"),
                new Department(null, "Регионоведение", "Истории", "09.01.05"),
                new Department(null, "Антропология и этнология", "Истории", "09.01.06"),
                new Department(null, "Автоматизация технологических процессов и производств", "Кибернетики", "10.01.01"),
                new Department(null, "Автоматизированные системы обработки информации и управления", "Кибернетики", "10.01.02"),
                new Department(null, "Сервис транспортных средств", "Механики", "11.01.01"),
                new Department(null, "Фундаментальная математика", "Математики", "12.01.01"),
                new Department(null, "Математические методы экономики", "Математики", "12.01.02"),
                new Department(null, "Математика и экономическая теория", "Математики", "12.01.03"),
                new Department(null, "Прикладная математика и информатика", "Математики", "12.01.04"),
                new Department(null, "Математика и информатика", "Математики", "12.01.05"),
                new Department(null, "Космические исследования и космонавтика", "Математики", "12.01.06"),
                new Department(null, "Механика", "Механики", "11.01.02"),
                new Department(null, "Машиностроение", "Механики", "11.01.03"),
                new Department(null, "Технологические машины и оборудование", "Механики", "11.01.04"),
                new Department(null, "Мехатроника и робототехника", "Механики", "11.01.05"),
                new Department(null, "Стратегическое управление экономическими и политическими процессами", "Политологии", "13.01.01"),
                new Department(null, "Общая политология", "Политологии", "13.01.02"),
                new Department(null, "Политическое управление в связях с общественностью", "Политологии", "13.01.03"),
                new Department(null, "Государственная политика и управление", "Политологии", "13.01.04"),
                new Department(null, "Политические технологии", "Политологии", "13.01.05"),
                new Department(null, "Мировая политика", "Политологии", "13.01.06"),
                new Department(null, "Психолого педагогическое образование", "Психологии", "14.01.01"),
                new Department(null, "Дефектологическое образование", "Психологии", "14.01.02"),
                new Department(null, "Психология", "Психологии", "14.01.03"),
                new Department(null, "Клиническая психология", "Психологии", "14.01.04"),
                new Department(null, "Психология делового администрирования", "Психологии", "14.01.05"),
                new Department(null, "Педагогика и психология девиантного поведения", "Психологии", "14.01.06"),
                new Department(null, "Радиотехника", "Радиотехники", "15.01.01"),
                new Department(null, "Конструирование и технология электронных средств", "Радиотехники", "15.01.02"),
                new Department(null, "Электроника и нанотехнологии", "Радиотехники", "15.01.03"),
                new Department(null, "Наноинженерия", "Радиотехники", "15.01.04"),
                new Department(null, "Инфокоммуникационные технологии и системы связи", "Радиотехники", "15.01.05"),
                new Department(null, "Прикладная информатика в социологии", "Социологии", "16.01.01"),
                new Department(null, "Социология управления", "Социологии", "16.01.02"),
                new Department(null, "Социология бизнеса", "Социологии", "16.01.03"),
                new Department(null, "Экономическая социология", "Социологии", "16.01.04"),
                new Department(null, "Социология права", "Социологии", "16.01.05"),
                new Department(null, "Социология религии", "Социологии", "16.01.06"),
                new Department(null, "Социология культуры", "Социологии", "16.01.07"),
                new Department(null, "Социология личности", "Социологии", "16.01.08"),
                new Department(null, "Военная социология", "Социологии", "16.01.09"),
                new Department(null, "Социология семьи", "Социологии", "16.01.10"),
                new Department(null, "Государственное и муниципальное управление", "Менеджмента", "17.01.01"),
                new Department(null, "Управление персоналом", "Менеджмента", "17.01.02"),
                new Department(null, "Организационное управление", "Менеджмента", "17.01.03"),
                new Department(null, "Управление производством", "Менеджмента", "17.01.04"),
                new Department(null, "Управление проектами", "Менеджмента", "17.01.05"),
                new Department(null, "Финансовое управление", "Менеджмента", "17.01.06"),
                new Department(null, "Прикладная математика и физика", "Физики", "18.01.01"),
                new Department(null, "Физическая информатика", "Физики", "18.01.02"),
                new Department(null, "Общая и фундаментальная физика", "Физики", "18.01.03"),
                new Department(null, "Астрономия", "Физики", "18.01.04"),
                new Department(null, "Радиофизика", "Физики", "18.01.05"),
                new Department(null, "Нанотехнологии и микросистемная техника", "Физики", "18.01.06"),
                new Department(null, "Классическая филология", "Филологии", "19.01.01"),
                new Department(null, "Общая лингвистика", "Филологии", "19.01.02"),
                new Department(null, "Коммуникативистика", "Филологии", "19.01.03"),
                new Department(null, "Литературоведение", "Филологии", "19.01.04"),
                new Department(null, "Прикладная лингвистика", "Филологии", "19.01.05"),
                new Department(null, "Учитель русского языка и литературы", "Филологии", "19.01.06"),
                new Department(null, "Копирайтинг", "Филологии", "19.01.07"),
                new Department(null, "Издательское дело", "Филологии", "19.01.08"),
                new Department(null, "Романо германское разделение", "Филологии", "19.01.09"),
                new Department(null, "Восточные языки", "Филологии", "19.01.10"),
                new Department(null, "Психолингвистика", "Филологии", "19.01.11"),
                new Department(null, "Современные медиа коммуникации", "Филологии", "19.01.12"),
                new Department(null, "Философия", "Философии", "20.01.01"),
                new Department(null, "Связи с общественностью и реклама", "Философии", "20.01.02"),
                new Department(null, "Культурология", "Философии", "20.01.03"),
                new Department(null, "Биотехнология", "Химии", "21.01.01"),
                new Department(null, "Фармация", "Химии", "21.01.02"),
                new Department(null, "Фундаментальная и прикладная химия", "Химии", "21.01.03"),
                new Department(null, "Химическая технология", "Химии", "21.01.04"),
                new Department(null, "Техносферная безопасность", "Химии", "21.01.05"),
                new Department(null, "Разработка биофармацевтических препаратов", "Химии", "21.01.06"),
                new Department(null, "Аналитическая химия", "Химии", "21.01.07"),
                new Department(null, "Органическая химия", "Химии", "21.01.08"),
                new Department(null, "Живопись", "Искусства и графики", "22.01.01"),
                new Department(null, "Графика", "Искусства и графики", "22.01.02"),
                new Department(null, "Скульптура", "Искусства и графики", "22.01.03"),
                new Department(null, "Дизайн", "Искусства и графики", "22.01.04"),
                new Department(null, "Декоративно прикладное искусство и народные промыслы", "Искусства и графики", "22.01.05"),
                new Department(null, "Графический дизайн", "Искусства и графики", "22.01.06"),
                new Department(null, "Иллюстратор", "Искусства и графики", "22.01.07"),
                new Department(null, "Моделирование 3D", "Искусства и графики", "22.01.08"),
                new Department(null, "Дизайн интерьера", "Искусства и графики", "22.01.09"),
                new Department(null, "Учитель изобразительного искусства", "Искусства и графики", "22.01.10"),
                new Department(null, "Художник постановщик", "Искусства и графики", "22.01.11"),
                new Department(null, "Реставратор", "Искусства и графики", "22.01.12"),
                new Department(null, "Экономика и управление", "Экономики", "23.01.01"),
                new Department(null, "Финансы и кредит", "Экономики", "23.01.02"),
                new Department(null, "Международная экономика", "Экономики", "23.01.03"),
                new Department(null, "Бухгалтерский учёт и аудит", "Экономики", "23.01.04"),
                new Department(null, "Экономическая безопасность", "Экономики", "23.01.05"),
                new Department(null, "Радиоэлектронные системы и комплексы", "Электроники", "24.01.01"),
                new Department(null, "Лазерные технологии", "Электроники", "24.01.02"),
                new Department(null, "Электроника и автоматизация физических установок", "Электроники", "24.01.03"),
                new Department(null, "Электронные и оптоэлектронные устройства в специальных системах", "Электроники", "24.01.04"),
                new Department(null, "Гражданское право", "Юридического", "25.01.01"),
                new Department(null, "Юриспруденция", "Юридического", "25.01.02"),
                new Department(null, "Уголовное право", "Юридического", "25.01.03"),
                new Department(null, "Природно ресурсное аграрное и экологическое право", "Юридического", "25.01.04"),
                new Department(null, "Государственное право", "Юридического", "25.01.05"),
                new Department(null, "Теория и история государства и права", "Юридического", "25.01.06"),
                new Department(null, "Право и экономика", "Юридического", "25.01.07")
        );
        departmentRepo.saveAll(departments);
    }

    public void initallsubjects() {
        List<Subject> allsubjects = Arrays.asList(
                new Subject(null, "Математика (базовый уровень)"),
                new Subject(null, "Математика (профильный уровень)"),
                new Subject(null, "Русский язык"),
                new Subject(null, "Физика"),
                new Subject(null, "Химия"),
                new Subject(null, "История"),
                new Subject(null, "Обществознание"),
                new Subject(null, "Информатика"),
                new Subject(null, "Биология"),
                new Subject(null, "География"),
                new Subject(null, "Английский язык"),
                new Subject(null, "Немецкий язык"),
                new Subject(null, "Французский язык"),
                new Subject(null, "Испанский язык"),
                new Subject(null, "Китайский язык"),
                new Subject(null, "Литература")
        );
        subjectRepo.saveAll(allsubjects);
    }

    public void initallCompetitionGroups() {

        List<Department> departments = departmentRepo.findAll();

        List<CompetitionGroup> competitionGroups = new ArrayList<>();

        for (Department department : departments) {
            competitionGroups.add(new CompetitionGroup(null,
                    department.getTitle() + "-" +
                            categoryRepo.findById(1L).get().getTitle() + "-" +
                            formOfEducationRepo.findById(1L).get().getTitle(), 70, department,
                    categoryRepo.findById(1L).get(), formOfEducationRepo.findById(1L).get()));

			competitionGroups.add(new CompetitionGroup(null,
					department.getTitle() + "-" +
							categoryRepo.findById(2L).get().getTitle() + "-" +
							formOfEducationRepo.findById(1L).get().getTitle(), 20, department,
					categoryRepo.findById(2L).get(), formOfEducationRepo.findById(1L).get()));

			competitionGroups.add(new CompetitionGroup(null,
					department.getTitle() + "-" +
							categoryRepo.findById(3L).get().getTitle() + "-" +
							formOfEducationRepo.findById(1L).get().getTitle(), 10, department,
					categoryRepo.findById(3L).get(), formOfEducationRepo.findById(1L).get()));


			competitionGroups.add(new CompetitionGroup(null,
					department.getTitle() + "-" +
							categoryRepo.findById(1L).get().getTitle() + "-" +
							formOfEducationRepo.findById(2L).get().getTitle(), 70, department,
					categoryRepo.findById(1L).get(), formOfEducationRepo.findById(2L).get()));

			competitionGroups.add(new CompetitionGroup(null,
					department.getTitle() + "-" +
							categoryRepo.findById(2L).get().getTitle() + "-" +
							formOfEducationRepo.findById(2L).get().getTitle(), 20, department,
					categoryRepo.findById(2L).get(), formOfEducationRepo.findById(2L).get()));

			competitionGroups.add(new CompetitionGroup(null,
					department.getTitle() + "-" +
							categoryRepo.findById(3L).get().getTitle() + "-" +
							formOfEducationRepo.findById(2L).get().getTitle(), 10, department,
					categoryRepo.findById(3L).get(), formOfEducationRepo.findById(2L).get()));
        }
        competitionGroupRepo.saveAll(competitionGroups);
    }

    public void initSubjectOfDepartments() {
        List<SubjectOfDepartment> SubjectsOfDepartments = Arrays.asList(
                // "Биологии" - "Физико химическая биология"
                new SubjectOfDepartment(null, 1, subjectRepo.findByTitle("Русский язык"), departmentRepo.findByTitle("Физико химическая биология")),
                new SubjectOfDepartment(null, 2, subjectRepo.findByTitle("Математика (базовый уровень)"), departmentRepo.findByTitle("Физико химическая биология")),
                new SubjectOfDepartment(null, 3, subjectRepo.findByTitle("Биология"), departmentRepo.findByTitle("Физико химическая биология")),
                new SubjectOfDepartment(null, 3, subjectRepo.findByTitle("Химия"), departmentRepo.findByTitle("Физико химическая биология")),

                // Новый department: "Информации" - "Информатика и вычислительная техника"
                new SubjectOfDepartment(null, 2, subjectRepo.findByTitle("Математика (профильный уровень)"), departmentRepo.findByTitle("Информатика и вычислительная техника")),
                new SubjectOfDepartment(null, 3, subjectRepo.findByTitle("Информатика"), departmentRepo.findByTitle("Информатика и вычислительная техника")),
                new SubjectOfDepartment(null, 3, subjectRepo.findByTitle("Физика"), departmentRepo.findByTitle("Информатика и вычислительная техника")),
                new SubjectOfDepartment(null, 1, subjectRepo.findByTitle("Русский язык"), departmentRepo.findByTitle("Информатика и вычислительная техника"))
        );
        SubjectOfDepartmentRepo.saveAll(SubjectsOfDepartments);
    }

}
