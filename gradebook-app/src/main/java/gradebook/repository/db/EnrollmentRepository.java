package gradebook.repository.db;

import gradebook.repository.db.data.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
    Optional<EnrollmentEntity> findByStudentIdAndCourseId(int studentId, int courseId);
}
