package gradebook.repository.db;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "grade")
    private int grade;

}
