package gradebook.repository.db;

import gradebook.repository.db.data.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Optional<Enrollment> findByStudentIdAndCourseId(int studentId, int courseId);
}
