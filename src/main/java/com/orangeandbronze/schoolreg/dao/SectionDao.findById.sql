SELECT sections.*, s1.subject_id, faculty.faculty_number, subject_prerequisites.fk_prerequisite, s2.subject_id AS prerequisites FROM sections INNER JOIN subjects AS s1 ON sections.fk_subject = s1.pk INNER JOIN faculty ON sections.fk_faculty = faculty.pk LEFT JOIN subject_prerequisites ON s1.pk = subject_prerequisites.fk_subject LEFT JOIN subjects AS s2 ON subject_prerequisites.fk_prerequisite = s2.pk WHERE section_number = ? order by pk;
