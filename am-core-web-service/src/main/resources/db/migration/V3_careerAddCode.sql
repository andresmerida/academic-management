ALTER TABLE career
    ADD COLUMN code varchar(10);


CREATE UNIQUE INDEX idx_career_code ON career(code);

--para hacer esto es necesario llenar los datos de code en los datos ya creados anteriormente de carrera
ALTER TABLE career
    ALTER COLUMN code SET NOT NULL;