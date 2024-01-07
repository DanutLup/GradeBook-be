CREATE TABLE IF NOT EXISTS public.courses
(
    course_id integer NOT NULL,
    credits integer NOT NULL,
    teacher_id integer NOT NULL,
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

