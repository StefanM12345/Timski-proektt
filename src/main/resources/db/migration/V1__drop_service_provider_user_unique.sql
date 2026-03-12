-- Овозможува повеќе огласи по еден давач (user може да има повеќе service_provider записи)
-- Отстранува unique constraint на user_id (работи со било кое име на constraint)
DO $$
DECLARE
    r RECORD;
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'service_provider') THEN
        FOR r IN
            SELECT c.conname
            FROM pg_constraint c
            JOIN pg_class t ON c.conrelid = t.oid
            WHERE t.relname = 'service_provider'
              AND c.contype = 'u'
              AND EXISTS (
                  SELECT 1 FROM pg_attribute a
                  WHERE a.attrelid = c.conrelid
                    AND a.attnum = ANY(c.conkey)
                    AND a.attname = 'user_id'
                    AND NOT a.attisdropped
              )
        LOOP
            EXECUTE format('ALTER TABLE service_provider DROP CONSTRAINT %I', r.conname);
        END LOOP;
    END IF;
END $$;
