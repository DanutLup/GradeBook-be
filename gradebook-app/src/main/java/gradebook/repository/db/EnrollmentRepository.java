package gradebook.repository.db;

import gradebook.repository.db.data.EnrollmentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
  Optional<EnrollmentEntity> findByStudentIdAndCourseId(int studentId, int courseId);
}
