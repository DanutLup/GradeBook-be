CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.courses
(
    course_id integer NOT NULL,
    credits integer NOT NULL,
    teacher_id integer NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE SEQUENCE courses_seq;

CREATE TABLE IF NOT EXISTS public.enrollments
(
    course_id integer NOT NULL,
    date date,
    enrollment_id integer NOT NULL,
    grade integer,
    student_id integer NOT NULL,
    CONSTRAINT enrollments_pkey PRIMARY KEY (enrollment_id)
);

CREATE SEQUENCE enrollments_seq;

CREATE TABLE IF NOT EXISTS public.students
(
    user_id integer NOT NULL,
    student_code character varying(255),
    CONSTRAINT students_pkey PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS public.teachers
(
    user_id integer NOT NULL,
    teacher_code character varying(255),
    CONSTRAINT teachers_pkey PRIMARY KEY (user_id)
);

CREATE SEQUENCE users_seq;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL,
    email character varying(255) NOT NULL UNIQUE,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    user_role character varying(255) NOT NULL,
    cnp character varying(255) NOT NULL UNIQUE,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS public.courses
    ADD CONSTRAINT fk468oyt88pgk2a0cxrvxygadqg FOREIGN KEY (teacher_id)
    REFERENCES public.teachers (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.enrollments
    ADD CONSTRAINT fk8kf1u1857xgo56xbfmnif2c51 FOREIGN KEY (student_id)
    REFERENCES public.students (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.enrollments
    ADD CONSTRAINT fkho8mcicp4196ebpltdn9wl6co FOREIGN KEY (course_id)
    REFERENCES public.courses (course_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.students
    ADD CONSTRAINT fkdt1cjx5ve5bdabmuuf3ibrwaq FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS students_pkey
    ON public.students(user_id);


ALTER TABLE IF EXISTS public.teachers
    ADD CONSTRAINT fkb8dct7w2j1vl1r2bpstw5isc0 FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS teachers_pkey
    ON public.teachers(user_id);

INSERT INTO users(user_id, email, password, first_name, last_name, user_role, cnp)
VALUES(0, 'danut.lup@gradebook.com', 'password', 'Danut', 'Lup', 'ADMIN', '1234567890');

INSERT INTO public.users(user_id, email, password, first_name, last_name, user_role, cnp)
VALUES
  (1, 'admin@gradebook.com', 'adminpassword', 'Admin', 'User', 'ADMIN', '1234567891');

INSERT INTO public.users(user_id, email, password, first_name, last_name, user_role, cnp)
VALUES
  (2, 'student1@example.com', 'studentpassword', 'John', 'Doe', 'STUDENT', '1111111111'),
  (3, 'student2@example.com', 'studentpassword', 'Jane', 'Smith', 'STUDENT', '2222222222'),
  (4, 'student3@example.com', 'studentpassword', 'Bob', 'Johnson', 'STUDENT', '3333333333'),
  (5, 'student4@example.com', 'studentpassword', 'Alice', 'Williams', 'STUDENT', '4444444444'),
  (6, 'student5@example.com', 'studentpassword', 'Charlie', 'Brown', 'STUDENT', '5555555555'),
  (7, 'student6@example.com', 'studentpassword', 'Eva', 'Lee', 'STUDENT', '6666666666'),
  (8, 'student7@example.com', 'studentpassword', 'David', 'Miller', 'STUDENT', '7777777777'),
  (9, 'student8@example.com', 'studentpassword', 'Grace', 'Davis', 'STUDENT', '8888888888'),
  (10, 'student9@example.com', 'studentpassword', 'Sam', 'Wilson', 'STUDENT', '9999999999'),
  (11, 'student10@example.com', 'studentpassword', 'Sophie', 'Johnson', 'STUDENT', '1010101010'),
  (12, 'student11@example.com', 'studentpassword', 'Henry', 'Clark', 'STUDENT', '1212121212'),
  (13, 'student12@example.com', 'studentpassword', 'Olivia', 'Smith', 'STUDENT', '1313131313'),
  (14, 'student13@example.com', 'studentpassword', 'Mason', 'Adams', 'STUDENT', '1414141414'),
  (15, 'student14@example.com', 'studentpassword', 'Chloe', 'Walker', 'STUDENT', '1515151515'),
  (16, 'student15@example.com', 'studentpassword', 'Logan', 'Evans', 'STUDENT', '1616161616'),
  (17, 'student16@example.com', 'studentpassword', 'Zoe', 'Parker', 'STUDENT', '1717171717'),
  (18, 'student17@example.com', 'studentpassword', 'Liam', 'Ward', 'STUDENT', '1818181818'),
  (19, 'student18@example.com', 'studentpassword', 'Ava', 'Baker', 'STUDENT', '1919191919'),
  (20, 'student19@example.com', 'studentpassword', 'Ethan', 'Cooper', 'STUDENT', '2020202020'),
  (21, 'student20@example.com', 'studentpassword', 'Emma', 'Fisher', 'STUDENT', '2121212121');

-- Insert data into the 'users' table for teachers
INSERT INTO public.users(user_id, email, password, first_name, last_name, user_role, cnp)
VALUES
  (22, 'teacher1@example.com', 'teacherpassword', 'Prof', 'Johnson', 'TEACHER', '2222000000'),
  (23, 'teacher2@example.com', 'teacherpassword', 'Dr', 'Smith', 'TEACHER', '2333000000'),
  (24, 'teacher3@example.com', 'teacherpassword', 'Ms', 'Williams', 'TEACHER', '2444000000'),
  (25, 'teacher4@example.com', 'teacherpassword', 'Mr', 'Brown', 'TEACHER', '2555000000'),
  (26, 'teacher5@example.com', 'teacherpassword', 'Mrs', 'Lee', 'TEACHER', '2666000000'),
  (27, 'teacher6@example.com', 'teacherpassword', 'Prof', 'Miller', 'TEACHER', '2777000000'),
  (28, 'teacher7@example.com', 'teacherpassword', 'Dr', 'Davis', 'TEACHER', '2888000000');

-- Insert data into the 'students' table
INSERT INTO public.students(user_id, student_code)
VALUES
  (2, 'STU001'), (3, 'STU002'), (4, 'STU003'), (5, 'STU004'), (6, 'STU005'),
  (7, 'STU006'), (8, 'STU007'), (9, 'STU008'), (10, 'STU009'), (11, 'STU010'),
  (12, 'STU011'), (13, 'STU012'), (14, 'STU013'), (15, 'STU014'), (16, 'STU015'),
  (17, 'STU016'), (18, 'STU017'), (19, 'STU018'), (20, 'STU019'), (21, 'STU020');

-- Insert data into the 'teachers' table
INSERT INTO public.teachers(user_id, teacher_code)
VALUES
  (22, 'TCH001'), (23, 'TCH002'), (24, 'TCH003'), (25, 'TCH004'), (26, 'TCH005'),
  (27, 'TCH006'), (28, 'TCH007');

-- Insert data into the 'courses' table
INSERT INTO public.courses(course_id, credits, teacher_id, name)
VALUES
  (1, 3, 22, 'Computer Science 101'),
  (2, 4, 23, 'Database Fundamentals'),
  (3, 3, 24, 'Web Development Basics'),
  (4, 3, 25, 'Data Structures and Algorithms'),
  (5, 4, 26, 'Software Engineering Principles'),
  (6, 3, 27, 'Mobile App Development'),
  (7, 3, 28, 'Introduction to Artificial Intelligence');

  ALTER SEQUENCE users_seq RESTART WITH 30;
  ALTER SEQUENCE courses_seq RESTART WITH 10;


