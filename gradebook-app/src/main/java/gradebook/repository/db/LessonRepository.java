package gradebook.repository.db;

import gradebook.repository.db.data.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {}
