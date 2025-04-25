package gradebook.repository.db;

import gradebook.repository.db.data.LessonEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {
  List<LessonEntity> findByCourseId(Integer courseId);
}
