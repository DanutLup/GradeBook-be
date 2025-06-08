package gradebook.repository.db;

import gradebook.repository.db.data.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository
    extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity> {}
