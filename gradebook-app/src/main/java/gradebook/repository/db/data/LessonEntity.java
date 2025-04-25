package gradebook.repository.db.data;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonEntity {

  @Column(name = "lesson_id")
  @Id
  @SequenceGenerator(name = "lesson_generator", sequenceName = "lessons_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_generator")
  private int id;

  @Column(name = "description")
  private String description;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private LessonType type;

  @Column(name = "content")
  private String content;

  @Column(name = "created")
  private Date created;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private CourseEntity course;
}
