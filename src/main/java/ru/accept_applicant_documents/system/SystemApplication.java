package ru.accept_applicant_documents.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.accept_applicant_documents.system.enums.AllSubjects;
import ru.accept_applicant_documents.system.enums.Roles;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Department;
import ru.accept_applicant_documents.system.model.ExamResult;
import ru.accept_applicant_documents.system.repository.AdminRepo;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;
import ru.accept_applicant_documents.system.repository.DepartmentRepo;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;
import ru.accept_applicant_documents.system.service.AdminService;
import ru.accept_applicant_documents.system.service.ApplicantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class 	SystemApplication implements CommandLineRunner {

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
	ExamResultRepo examResultRepo;
	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public void run(String... args) throws Exception {
		// Создание админа по умолчанию
		if (!adminService.AdminAccountAlreadyExist("admin@mail.ru"))
		{
			adminRepo.save(new Admin(null, "admin@mail.ru", "Адольф",
					bCryptPasswordEncoder.encode("12345678"), Roles.ADMIN));
		}
		if (departmentRepo.findById(1L).isEmpty())
			init();
	}

	public void init() {
		List<Department> departments = Arrays.asList(
				new Department(null, "Физико химическая биология", "Биологии"),
				new Department(null, "Общая биология", "Биологии"),
				new Department(null, "Экология и природопользование", "Биологии"),
				new Department(null, "Теология", "Теологии"),
				new Department(null, "Религиоведение", "Теологии"),
				new Department(null, "Филология", "Филологии"),
				new Department(null, "Социальная работа", "Социологии"),
				new Department(null, "География", "Географии"),
				new Department(null, "Картография и геоинформатика", "Географии"),
				new Department(null, "Гидрометеорология", "Географии"),
				new Department(null, "Туризм", "Географии"),
				new Department(null, "Геология и полезные ископаемые", "Геологии"),
				new Department(null, "Геофизика", "Геологии"),
				new Department(null, "Геохимия", "Геологии"),
				new Department(null, "Геология и геохимия полезных ископаемых", "Геологии"),
				new Department(null, "Гидрогеология инженерная геология геокриология", "Геологии"),
				new Department(null, "Экологическая геология", "Геологии"),
				new Department(null, "Журналистика", "Журналистики"),
				new Department(null, "Международная журналистика", "Журналистики"),
				new Department(null, "Мультимедийная журналистика", "Журналистики"),
				new Department(null, "Периодическая печать", "Журналистики"),
				new Department(null, "Телевизионная журналистика", "Журналистики"),
				new Department(null, "Интернет журналистика", "Журналистики"),
				new Department(null, "Бизнес журналистика", "Журналистики"),
				new Department(null, "Политическая журналистика", "Журналистики"),
				new Department(null, "Информатика и вычислительная техника", "Информации"),
				new Department(null, "Прикладная информатика", "Информации"),
				new Department(null, "Программная инженерия", "Информации"),
				new Department(null, "Информационная безопасность", "Информации"),
				new Department(null, "Приборостроение", "Информации"),
				new Department(null, "Инноватика", "Информации"),
				new Department(null, "Бизнес информатика", "Информации"),
				new Department(null, "История", "Истории"),
				new Department(null, "Документоведение", "Истории"),
				new Department(null, "Музееведение и охрана историко культурного наследия", "Истории"),
				new Department(null, "Историко архивоведение", "Истории"),
				new Department(null, "Регионоведение", "Истории"),
				new Department(null, "Антропология и этнология", "Истории"),
				new Department(null, "Автоматизация технологических процессов и производств", "Кибернетики"),
				new Department(null, "Автоматизированные системы обработки информации и управления", "Кибернетики"),
				new Department(null, "Сервис транспортных средств", "Механики"),
				new Department(null, "Фундаментальная математика", "Математики"),
				new Department(null, "Математические методы экономики", "Математики"),
				new Department(null, "Математика и экономическая теория", "Математики"),
				new Department(null, "Прикладная математика и информатика", "Математики"),
				new Department(null, "Математика и информатика", "Математики"),
				new Department(null, "Космические исследования и космонавтика", "Математики"),
				new Department(null, "Механика", "Механики"),
				new Department(null, "Машиностроение", "Механики"),
				new Department(null, "Технологические машины и оборудование", "Механики"),
				new Department(null, "Мехатроника и робототехника", "Механики"),
				new Department(null, "Стратегическое управление экономическими и политическими процессами", "Политологии"),
				new Department(null, "Общая политология", "Политологии"),
				new Department(null, "Политическое управление в связях с общественностью", "Политологии"),
				new Department(null, "Государственная политика и управление", "Политологии"),
				new Department(null, "Политические технологии", "Политологии"),
				new Department(null, "Мировая политика", "Политологии"),
				new Department(null, "Психолого педагогическое образование", "Психологии"),
				new Department(null, "Дефектологическое образование", "Психологии"),
				new Department(null, "Психология", "Психологии"),
				new Department(null, "Клиническая психология", "Психологии"),
				new Department(null, "Психология делового администрирования", "Психологии"),
				new Department(null, "Педагогика и психология девиантного поведения", "Психологии"),
				new Department(null, "Радиотехника", "Радиотехники"),
				new Department(null, "Конструирование и технология электронных средств", "Радиотехники"),
				new Department(null, "Электроника и нанотехнологии", "Радиотехники"),
				new Department(null, "Наноинженерия", "Радиотехники"),
				new Department(null, "Инфокоммуникационные технологии и системы связи", "Радиотехники"),
				new Department(null, "Прикладная информатика в социологии", "Социологии"),
				new Department(null, "Социология управления", "Социологии"),
				new Department(null, "Социология бизнеса", "Социологии"),
				new Department(null, "Экономическая социология", "Социологии"),
				new Department(null, "Социология права", "Социологии"),
				new Department(null, "Социология религии", "Социологии"),
				new Department(null, "Социология культуры", "Социологии"),
				new Department(null, "Социология личности", "Социологии"),
				new Department(null, "Военная социология", "Социологии"),
				new Department(null, "Социология семьи", "Социологии"),
				new Department(null, "Государственное и муниципальное управление", "Менеджмента"),
				new Department(null, "Управление персоналом", "Менеджмента"),
				new Department(null, "Организационное управление", "Менеджмента"),
				new Department(null, "Управление производством", "Менеджмента"),
				new Department(null, "Управление проектами", "Менеджмента"),
				new Department(null, "Финансовое управление", "Менеджмента"),
				new Department(null, "Прикладная математика и физика", "Физики"),
				new Department(null, "Физическая информатика", "Физики"),
				new Department(null, "Общая и фундаментальная физика", "Физики"),
				new Department(null, "Астрономия", "Физики"),
				new Department(null, "Радиофизика", "Физики"),
				new Department(null, "Нанотехнологии и микросистемная техника", "Физики"),
				new Department(null, "Классическая филология", "Филологии"),
				new Department(null, "Общая лингвистика", "Филологии"),
				new Department(null, "Коммуникативистика", "Филологии"),
				new Department(null, "Литературоведение", "Филологии"),
				new Department(null, "Прикладная лингвистика", "Филологии"),
				new Department(null, "Учитель русского языка и литературы", "Филологии"),
				new Department(null, "Копирайтинг", "Филологии"),
				new Department(null, "Издательское дело", "Филологии"),
				new Department(null, "Романо германское разделение", "Филологии"),
				new Department(null, "Восточные языки", "Филологии"),
				new Department(null, "Психолингвистика", "Филологии"),
				new Department(null, "Современные медиа коммуникации", "Филологии"),
				new Department(null, "Философия", "Философии"),
				new Department(null, "Связи с общественностью и реклама", "Философии"),
				new Department(null, "Культурология", "Философии"),
				new Department(null, "Биотехнология", "Химии"),
				new Department(null, "Фармация", "Химии"),
				new Department(null, "Фундаментальная и прикладная химия", "Химии"),
				new Department(null, "Химическая технология", "Химии"),
				new Department(null, "Техносферная безопасность", "Химии"),
				new Department(null, "Разработка биофармацевтических препаратов", "Химии"),
				new Department(null, "Аналитическая химия", "Химии"),
				new Department(null, "Органическая химия", "Химии"),
				new Department(null, "Живопись", "Искусства и графики"),
				new Department(null, "Графика", "Искусства и графики"),
				new Department(null, "Скульптура", "Искусства и графики"),
				new Department(null, "Дизайн", "Искусства и графики"),
				new Department(null, "Декоративно прикладное искусство и народные промыслы", "Искусства и графики"),
				new Department(null, "Графический дизайн", "Искусства и графики"),
				new Department(null, "Иллюстратор", "Искусства и графики"),
				new Department(null, "Моделирование 3D", "Искусства и графики"),
				new Department(null, "Дизайн интерьера", "Искусства и графики"),
				new Department(null, "Учитель изобразительного искусства", "Искусства и графики"),
				new Department(null, "Художник постановщик", "Искусства и графики"),
				new Department(null, "Реставратор", "Искусства и графики"),
				new Department(null, "Экономика и управление", "Экономики"),
				new Department(null, "Финансы и кредит", "Экономики"),
				new Department(null, "Международная экономика", "Экономики"),
				new Department(null, "Бухгалтерский учёт и аудит", "Экономики"),
				new Department(null, "Экономическая безопасность", "Экономики"),
				new Department(null, "Радиоэлектронные системы и комплексы", "Электроники"),
				new Department(null, "Лазерные технологии", "Электроники"),
				new Department(null, "Электроника и автоматизация физических установок", "Электроники"),
				new Department(null, "Электронные и оптоэлектронные устройства в специальных системах", "Электроники"),
				new Department(null, "Гражданское право", "Юридического"),
				new Department(null, "Юриспруденция", "Юридического"),
				new Department(null, "Уголовное право", "Юридического"),
				new Department(null, "Природно ресурсное аграрное и экологическое право", "Юридического"),
				new Department(null, "Государственное право", "Юридического"),
				new Department(null, "Теория и история государства и права", "Юридического"),
				new Department(null, "Право и экономика", "Юридического")
		);
		departmentRepo.saveAll(departments);
	}
}
