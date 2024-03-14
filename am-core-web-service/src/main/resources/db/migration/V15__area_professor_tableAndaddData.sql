CREATE TABLE area_professor(
    area_id INTEGER,
    professor_id INTEGER,
    active BOOLEAN NOT NULL,
    PRIMARY KEY (area_id, professor_id)
);
alter table area_professor  add constraint fk_area_professor_ref_area foreign key (area_id)
    references area(id) on delete restrict on update restrict;

alter table area_professor  add constraint fk_area_professor_ref_professor foreign key (professor_id)
    references professor(id) on delete restrict on update restrict;

INSERT INTO professor (id, name, last_name, second_last_name) VALUES
                                                                  (1, 'Carlos', 'Flores', 'Mamani'),
                                                                  (2, 'María', 'García', 'Torrez'),
                                                                  (3, 'Jorge', 'Vargas', 'Cabrera'),
                                                                  (4, 'Alejandra', 'Chávez', 'Herrera');
INSERT INTO area_professor (area_id, professor_id, active) VALUES
                                                               (1, 1, true),(2, 1, true),
                                                               (1, 2, false),(2, 2, true),
                                                               (2, 3, true),(1, 3, true),
                                                               (2, 4, true);