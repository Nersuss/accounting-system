-- Функция триггера
CREATE OR REPLACE FUNCTION create_personal_file()
RETURNS TRIGGER AS $$
BEGIN
    -- Проверяем, изменился ли doc_status на VERIFIED
    IF NEW.doc_status = 'VERIFIED' AND OLD.doc_status IS DISTINCT FROM NEW.doc_status THEN
        -- Создаем запись в таблице PersonalFile
        INSERT INTO public.personal_file (additional_points, applicant_id)
        VALUES (0, NEW.id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера
CREATE TRIGGER after_doc_status_verified
AFTER UPDATE OF doc_status ON public.applicants
FOR EACH ROW
EXECUTE FUNCTION create_personal_file();