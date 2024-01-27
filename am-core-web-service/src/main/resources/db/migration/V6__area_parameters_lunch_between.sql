ALTER TABLE area_parameters
ADD COLUMN lunch_time_schedule BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN start_lunch_time_schedule TIME,
ADD COLUMN end_lunch_time_schedule TIME,
ADD COLUMN between_period SMALLINT NOT NULL DEFAULT 0;

UPDATE area_parameters
SET lunch_time_schedule = false, -- O asigna el valor predeterminado que desees
    between_period = 0 -- O asigna el valor predeterminado que desees
WHERE lunch_time_schedule IS NULL OR between_period IS NULL;
