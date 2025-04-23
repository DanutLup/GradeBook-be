package gradebook.repository.db;

import gradebook.repository.db.data.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {}
