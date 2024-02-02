INSERT INTO academic_period (id, year, name, start_date, end_date, active, area_id) VALUES
                                                                                        (1, 2024, '1 - Primer Semestre', '2024-02-12', '2023-06-12', true, 1),
                                                                                        (2, 2024, '2 - Segundo Semestre', '2024-08-12', '2024-12-12', true, 1),
                                                                                        (3, 2023, '1 - Primer Semestre', '2023-02-12', '2023-06-12', true, 1),
                                                                                        (4, 2023, '2 - Tercer Semestre', '2024-06-12', '2024-07-12', true, 1),
                                                                                        (5, 2023, '2 - Cuarto Semestre', '2024-01-12', '2024-02-12', true, 1);
SELECT setval('academic_period_sequence', 5, true);