package gradebook.repository.db.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "enrollments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Enrollment {
    @Column(name = "enrollment_id")
    @Id
    @SequenceGenerator(name="enrollment_generator", sequenceName="enrollments_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="enrollment_generator")
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(name = "grade")
    private int grade;

}
