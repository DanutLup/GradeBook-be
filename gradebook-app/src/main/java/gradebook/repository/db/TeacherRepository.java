package gradebook.repository.db;

import gradebook.repository.db.data.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherRepository
    extends JpaRepository<TeacherEntity, Integer>, JpaSpecificationExecutor<TeacherEntity> {}
