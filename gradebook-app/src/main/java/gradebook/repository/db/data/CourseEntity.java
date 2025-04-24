package gradebook.repository.db.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseEntity {
    @Id
    @SequenceGenerator(name="course_generator", sequenceName="courses_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="course_generator")
    @Column(name = "course_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private int credits;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollmentEntities;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;
}
