INSERT INTO schedule_period (
    start_time,
    end_time,
    weekday,
    active,
    area_id
)
VALUES
    ('08:15:00', '09:45:00', 'MONDAY', true, 1),
    ('09:45:00', '11:15:00', 'MONDAY', true, 1),
    ('06:45:00', '08:15:00', 'TUESDAY', true, 1),
    ('08:15:00', '09:45:00', 'TUESDAY', true, 1),

    ('09:00:00', '10:00:00', 'WEDNESDAY', true, 2),
    ('10:00:00', '11:00:00', 'WEDNESDAY', true, 2),
    ('13:00:00', '14:00:00', 'THURSDAY', true, 2),
    ('14:00:00', '15:00:00', 'THURSDAY', true, 2);