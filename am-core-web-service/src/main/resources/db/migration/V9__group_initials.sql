--crear_tabla_subject
CREATE TABLE subject (
                         id integer not null not null,
                         name VARCHAR(100) not null,
                         initials VARCHAR(10) not null,
                         active BOOLEAN not null,
                         area_id INTEGER,
                         primary key (id)
);
create sequence subject_sequence as integer increment 1;

alter table subject add constraint fk_subject_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;

--crear_tabla_grupo
CREATE TABLE group_table (
                             id integer not null,
                             identifier VARCHAR(10) not null ,
                             remark VARCHAR(100),
                             curriculum_id INTEGER, --Dudas
                             subject_id INTEGER, --Dudas
                             itinerary_id INTEGER,
                             primary key (id)
);

create sequence group_sequence as integer increment 1;

alter table group_table add constraint fk_group_ref_itinerary foreign key (itinerary_id)
    references itinerary (id) on delete restrict on update restrict;

alter table group_table add constraint fk_group_ref_subject foreign key (subject_id)
    references subject (id) on delete restrict on update restrict;

--crear_tabla_professor
CREATE TABLE professor (
                           id integer not null,
                           name VARCHAR(70) not null,
                           last_name VARCHAR(70) not null,
                           second_last_name VARCHAR(70),
                           primary key (id)
);

create sequence professor_sequence as integer increment 1;

--crear_tabla_schedule
CREATE TABLE schedule (
                          id integer not null,
                          start_time TIME not null,
                          end_time TIME not null,
                          weekday VARCHAR(10) not null,
                          assistant VARCHAR(100),
                          classroom_id INTEGER,
                          professor_id INTEGER,
                          group_id INTEGER,
                          primary key (id)
);

create sequence schedule_sequence as integer increment 1;

alter table schedule add constraint fk_schedule_ref_classroom foreign key (classroom_id)
    references classroom (id) on delete restrict on update restrict;

alter table schedule add constraint fk_schedule_ref_professor foreign key (professor_id)
    references professor (id) on delete restrict on update restrict;

alter table schedule add constraint fk_schedule_ref_group foreign key (group_id)
    references group_table (id) on delete restrict on update restrict;
