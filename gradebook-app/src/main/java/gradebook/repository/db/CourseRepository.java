package gradebook.repository.db;

import gradebook.repository.db.data.CourseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

  List<CourseEntity> findByNameContainingIgnoreCase(String name);
}
