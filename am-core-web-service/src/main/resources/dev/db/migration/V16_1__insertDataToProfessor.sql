INSERT INTO professor (id, name, last_name, second_last_name, active)
VALUES
    (1, 'Juan', 'Pérez', 'García', TRUE),
    (2, 'María', 'López', 'Martínez', TRUE),
    (3, 'Pedro', 'González', 'Rodríguez', TRUE);

SELECT setval('professor_sequence', 3, true); --next value will be 4