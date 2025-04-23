package gradebook.repository.db;

import gradebook.repository.db.data.UserEntity;
import gradebook.repository.db.data.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByCnp(String cnp);
    Page<UserEntity> findByRole(UserRole role, Pageable pageable);

}
