INSERT INTO professor (id, name, last_name, second_last_name)
VALUES (1, 'Juan', 'Perez', 'Gomez'),
       (2, 'Maria', 'Lopez', NULL),
       (3, 'Pedro', 'Garcia', 'Martinez'),
       (4, 'Ana', 'Martinez', 'Fernandez'),
       (5, 'Carlos', 'Rodriguez', NULL);

SELECT setval('professor_sequence', 5, true);  -- next value will be 6

INSERT INTO area_professor (area_id, professor_id)
VALUES (1, 1),
       (2, 2),
       (1, 3),
       (1, 4),
       (2, 5);