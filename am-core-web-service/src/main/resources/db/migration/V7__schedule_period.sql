CREATE TABLE schedule_period (
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    weekday VARCHAR(10) NOT NULL,
    active BOOLEAN NOT NULL,
    area_id INTEGER NOT NULL,
    PRIMARY KEY (start_time, end_time, weekday, area_id),
    FOREIGN KEY (area_id) REFERENCES area_parameters(area_id)
);

UPDATE schedule_period
SET active = true
WHERE active IS NULL;
