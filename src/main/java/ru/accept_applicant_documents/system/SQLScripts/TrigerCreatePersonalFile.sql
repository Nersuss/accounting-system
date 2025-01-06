CREATE OR REPLACE FUNCTION public.create_personal_file()
RETURNS trigger
LANGUAGE 'plpgsql'
AS $BODY$
DECLARE
    random_number INT;
BEGIN
    -- Проверяем, изменился ли doc_status на VERIFIED
    IF NEW.doc_status = 'VERIFIED' AND OLD.doc_status IS DISTINCT FROM NEW.doc_status THEN
        LOOP
            random_number := FLOOR(RANDOM() * 1000000); -- Генерация случайного числа от 0 до 999999
            -- Проверка уникальности
            IF NOT EXISTS (SELECT 1 FROM personal_file WHERE registration_number = random_number) THEN
                -- Создаем запись в таблице PersonalFile с уникальным номером
                INSERT INTO public.personal_file (registration_number, additional_points, applicant_id)
                VALUES (random_number, 0, NEW.id);
                RETURN NEW;
            END IF;
        END LOOP;
    END IF;
    RETURN NEW;
END;
$BODY$;

-- Создание триггера
CREATE TRIGGER after_doc_status_verified
AFTER UPDATE OF doc_status ON public.applicants
FOR EACH ROW
EXECUTE FUNCTION public.create_personal_file();
