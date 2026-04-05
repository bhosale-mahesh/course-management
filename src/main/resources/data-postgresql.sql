INSERT INTO instructor (id, name, email)
VALUES (1, 'Minerva McGonagall', 'mcgonagall@hogwarts.edu.in'),
       (2, 'Severus Snape', 'snape@hogwarts.edu.in'),
       (3, 'Remus Lupin', 'lupin@hogwarts.edu.in'),
       (4, 'Filius Flitwick', 'flitwick@hogwarts.edu.in'),
       (5, 'Pomona Sprout', 'sprout@hogwarts.edu.in');

INSERT INTO course (id, title, description, price, is_active, instructor_id)
VALUES (1, 'Advanced Potion Making', 'Master the art of brewing magical potions', 120.00, true, 1),
       (2, 'Transfiguration Mastery', 'Learn advanced transformation spells', 150.0, true, 1),
       (3, 'Defence against the Dark Arts', 'Defence magic against dark creatures', 140.0, true, 3),
       (4, 'Charms', 'Casting everyday magical charms.', 110.0, true, 4),
       (5, 'Herbology', 'Study magical plants and fungi.', 100.0, true, 5);

INSERT INTO student (id, name, email)
VALUES (1, 'Harry Potter', 'harry@hogwarts.edu.in'),
       (2, 'Hermione Granger', 'hermione@hogwarts.edu.in'),
       (3, 'Ron Weasley', 'ron@hogwarts.edu.in'),
       (4, 'Neville Longbottom', 'neville@hogwarts.edu.in'),
       (5, 'Draco Malfoy', 'draco@hogwarts.edu.in');

INSERT INTO student_course(student_id, course_id)
VALUES (1, 3),
       (1, 4),
       (1, 5),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (3, 3),
       (3, 4),
       (3, 5),
       (4, 2),
       (4, 5),
       (5, 1);

SELECT setval('instructor_id_seq', (SELECT MAX(id) FROM instructor));
SELECT setval('course_id_seq', (SELECT MAX(id) FROM course));
SELECT setval('student_id_seq', (SELECT MAX(id) FROM student));
