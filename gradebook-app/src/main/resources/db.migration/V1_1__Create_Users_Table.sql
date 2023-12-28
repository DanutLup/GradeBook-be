CREATE TABLE IF NOT EXISTS public.courses
(
    course_id integer NOT NULL,
    credits integer NOT NULL,
    teacher_id integer,
    name character varying(255) NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS public.enrollments
(
    course_id integer NOT NULL,
    date date,
    enrollment_id integer NOT NULL,
    grade integer,
    student_id integer NOT NULL,
    CONSTRAINT enrollments_pkey PRIMARY KEY (enrollment_id)
);

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

CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    user_role character varying(255) NOT NULL CHECK(user_role IN ('STUDENT', 'TEACHER', 'ADMIN')),
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

--INSERT INTO users(user_id, email, password, first_name, last_name, user_role)
--VALUES(0, 'danut.lup@gradebook.com', 'password', 'Danut', 'Lup', 'STUDENT'),
--        (1, 'andrei.baciu@gradebook.com', 'password', 'Andrei', 'Baciu', 'TEACHER'),
--        (2, 'mihail.sadoveanu@gradebook.com', 'password', 'Mihail', 'Sadoveanu', 'TEACHER'),
--        (3, 'constantin.brancusi@gradebook.com', 'password', 'Constantin', 'Brancusi', 'TEACHER'),
--        (4, 'mircea.eliade@gradebook.com', 'password', 'Mircea', 'Eliade', 'TEACHER'),
--        (5, 'albert.einstein@gradebook.com', 'password', 'Albert', 'Einstein', 'TEACHER'),
--        (6, 'galileo.galilei@gradebook.com', 'password', 'Galileo', 'Galilei', 'TEACHER'),
--        (7, 'isaac.newton@gradebook.com', 'password', 'Isaac', 'Newton', 'TEACHER'),
--        (8, 'john.smith@gradebook.com', 'password', 'John', 'Smith', 'STUDENT'),
--        (9, 'sophia.johnson@gradebook.com', 'password', 'Sophia', 'Johnson', 'TEACHER'),
--        (10, 'olivia.brown@gradebook.com', 'password', 'Olivia', 'Brown', 'TEACHER'),
--        (11, 'william.williams@gradebook.com', 'password', 'William', 'Williams', 'STUDENT'),
--        (12, 'michael.jones@gradebook.com', 'password', 'Michael', 'Jones', 'STUDENT'),
--        (13, 'emma.garcia@gradebook.com', 'password', 'Emma', 'Garcia', 'TEACHER'),
--        (14, 'james.davis@gradebook.com', 'password', 'James', 'Davis', 'TEACHER'),
--        (15, 'olivia.rodriguez@gradebook.com', 'password', 'Olivia', 'Rodriguez', 'STUDENT'),
--        (16, 'benjamin.martinez@gradebook.com', 'password', 'Benjamin', 'Martinez', 'STUDENT'),
--        (17, 'sophia.miller@gradebook.com', 'password', 'Sophia', 'Miller', 'TEACHER'),
--        (18, 'william.jones@gradebook.com', 'password', 'William', 'Jones', 'TEACHER'),
--        (19, 'olivia.davis@gradebook.com', 'password', 'Olivia', 'Davis', 'STUDENT'),
--        (20, 'william.rodriguez@gradebook.com', 'password', 'William', 'Rodriguez', 'STUDENT'),
--        (21, 'olivia.martinez@gradebook.com', 'password', 'Olivia', 'Martinez', 'TEACHER'),
--        (22, 'sophia.rodriguez@gradebook.com', 'password', 'Sophia', 'Rodriguez', 'TEACHER'),
--        (23, 'william.johnson@gradebook.com', 'password', 'William', 'Johnson', 'STUDENT'),
--        (24, 'michael.garcia@gradebook.com', 'password', 'Michael', 'Garcia', 'STUDENT'),
--        (25, 'emma.davis@gradebook.com', 'password', 'Emma', 'Davis', 'TEACHER'),
--        (26, 'olivia.rodriguez@gradebook.com', 'password', 'Olivia', 'Rodriguez', 'TEACHER'),
--        (27, 'benjamin.miller@gradebook.com', 'password', 'Benjamin', 'Miller', 'STUDENT'),
--        (28, 'olivia.brown@gradebook.com', 'password', 'Olivia', 'Brown', 'STUDENT'),
--        (29, 'michael.johnson@gradebook.com', 'password', 'Michael', 'Johnson', 'TEACHER'),
--        (30, 'emma.rodriguez@gradebook.com', 'password', 'Emma', 'Rodriguez', 'TEACHER'),
--        (31, 'william.garcia@gradebook.com', 'password', 'William', 'Garcia', 'STUDENT'),
--        (32, 'olivia.jones@gradebook.com', 'password', 'Olivia', 'Jones', 'STUDENT'),
--        (33, 'sophia.martinez@gradebook.com', 'password', 'Sophia', 'Martinez', 'TEACHER'),
--        (34, 'william.johnson@gradebook.com', 'password', 'William', 'Johnson', 'TEACHER'),
--        (35, 'olivia.smith@gradebook.com', 'password', 'Olivia', 'Smith', 'STUDENT'),
--        (36, 'michael.brown@gradebook.com', 'password', 'Michael', 'Brown', 'STUDENT'),
--        (37, 'emma.johnson@gradebook.com', 'password', 'Emma', 'Johnson', 'TEACHER'),
--        (38, 'olivia.smith@gradebook.com', 'password', 'Olivia', 'Smith', 'TEACHER'),
--        (39, 'benjamin.rodriguez@gradebook.com', 'password', 'Benjamin', 'Rodriguez', 'STUDENT'),
--        (40, 'olivia.martinez@gradebook.com', 'password', 'Olivia', 'Martinez', 'STUDENT'),
--        (41, 'michael.brown@gradebook.com', 'password', 'Michael', 'Brown', 'TEACHER'),
--        (42, 'emma.johnson@gradebook.com', 'password', 'Emma', 'Johnson', 'TEACHER'),
--        (43, 'olivia.smith@gradebook.com', 'password', 'Olivia', 'Smith', 'STUDENT'),
--        (44, 'william.garcia@gradebook.com', 'password', 'William', 'Garcia', 'STUDENT'),
--        (45, 'olivia.jones@gradebook.com', 'password', 'Olivia', 'Jones', 'TEACHER'),
--        (46, 'sophia.martinez@gradebook.com', 'password', 'Sophia', 'Martinez', 'TEACHER'),
--        (47, 'william.johnson@gradebook.com', 'password', 'William', 'Johnson', 'STUDENT'),
--        (48, 'olivia.smith@gradebook.com', 'password', 'Olivia', 'Smith', 'STUDENT');

