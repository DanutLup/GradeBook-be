CREATE SEQUENCE IF NOT EXISTS lessons_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS lessons (
                         lesson_id INT PRIMARY KEY DEFAULT nextval('lessons_seq'),
                         description VARCHAR(255),
                         title VARCHAR(128),
                         type VARCHAR(50),
                         content TEXT,
                         created TIMESTAMP,
                         course_id INT,
                         CONSTRAINT fk_course
                             FOREIGN KEY (course_id)
                                 REFERENCES courses(course_id)
                                 ON DELETE CASCADE
);
