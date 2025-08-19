--create display name trigger
CREATE
OR REPLACE FUNCTION set_display_name()
RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
display_name_changed BOOLEAN := (TG_OP = 'INSERT')
                                OR NEW.display_name IS DISTINCT FROM OLD.display_name;
first_last_changed BOOLEAN := (TG_OP = 'INSERT')
                                OR NEW.first_name IS DISTINCT FROM OLD.first_name
                                OR NEW.last_name  IS DISTINCT FROM OLD.last_name;
BEGIN

NEW.first_name := NULLIF(BTRIM(NEW.first_name), '');
NEW.last_name := NULLIF(BTRIM(NEW.last_name),  '');
NEW.display_name := NULLIF(BTRIM(NEW.display_name), '');

IF
TG_OP = 'INSERT'
THEN
    IF NEW.display_name IS NULL
    THEN
      NEW.display_name := CONCAT_WS(' ', NEW.first_name, NEW.last_name);
      NEW.display_name_auto := TRUE;
ELSE
      NEW.display_name_auto := FALSE;
END IF;
RETURN NEW;
END IF;

IF
TG_OP = 'UPDATE' AND display_name_changed
THEN
    IF NEW.display_name IS NULL
    THEN
        NEW.display_name := CONCAT_WS(' ', NEW.first_name, NEW.last_name);
        NEW.display_name_auto := TRUE;
    ELSE
        NEW.display_name_auto := FALSE;
    END IF;
ELSIF
OLD.display_name_auto AND first_last_changed
THEN
    NEW.display_name := CONCAT_WS(' ', NEW.first_name, NEW.last_name);
    NEW.display_name_auto := TRUE;
ELSE
    NEW.display_name := OLD.display_name;
    NEW.display_name_auto := FALSE;
END IF;

RETURN NEW;
END $$;

CREATE TRIGGER trg_set_display_name
    BEFORE INSERT OR
UPDATE OF first_name, last_name, display_name
ON author
    FOR EACH ROW EXECUTE FUNCTION set_display_name();