--<ScriptOptions statementTerminator=";"/>

CREATE TABLE sections (
	pk INT NOT NULL,
	section_number VARCHAR(20),
	fk_subject INT,
	fk_faculty INT,
	schedule VARCHAR(20),
	term VARCHAR(20),
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE TABLE faculty (
	pk INT NOT NULL,
	faculty_number INT,
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE TABLE students (
	pk INT NOT NULL,
	student_number INT,
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE TABLE terms (
	term VARCHAR(20) NOT NULL,
	PRIMARY KEY (term)
) ENGINE=InnoDB;

CREATE TABLE subject_prerequisites (
	pk INT NOT NULL,
	fk_subject INT,
	fk_prerequisite INT,
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE TABLE subjects (
	pk INT NOT NULL,
	subject_id VARCHAR(20),
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE TABLE enrollment_sections (
	pk INT NOT NULL,
	fk_enrollment INT,
	fk_sections INT,
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE TABLE enrollments (
	pk INT NOT NULL,
	enrollment_number INT,
	fk_students INT,
	term VARCHAR(20),
	PRIMARY KEY (pk)
) ENGINE=InnoDB;

CREATE INDEX fk_subject ON sections (fk_subject ASC);

CREATE INDEX fk_faculty ON sections (fk_faculty ASC);

CREATE INDEX fk_sections ON enrollment_sections (fk_sections ASC);

CREATE UNIQUE INDEX section_number ON sections (section_number ASC);

CREATE INDEX fk_enrollment ON enrollment_sections (fk_enrollment ASC);

CREATE UNIQUE INDEX faculty_number ON faculty (faculty_number ASC);

CREATE UNIQUE INDEX enrollment_number ON enrollments (enrollment_number ASC);

CREATE UNIQUE INDEX student_number ON students (student_number ASC);

CREATE INDEX fk_students ON enrollments (fk_students ASC);

CREATE INDEX fk_prerequisite ON subject_prerequisites (fk_prerequisite ASC);

CREATE UNIQUE INDEX subject_id ON subjects (subject_id ASC);

CREATE INDEX fk_subject ON subject_prerequisites (fk_subject ASC);

