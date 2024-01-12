
ALTER TABLE career
    ALTER COLUMN initials TYPE varchar(10);


ALTER TABLE career
    ADD CONSTRAINT chk_initials_length CHECK (LENGTH(initials) <= 10);