package gradebook.service.user;

import gradebook.repository.db.data.StudentEntity;
import gradebook.repository.db.data.TeacherEntity;
import jakarta.persistence.criteria.*;
import java.util.Locale;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class UserCriteriaBuilder {

  public Specification<StudentEntity> nameMatchesPrefixForStudent(String input) {
    return (Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      if (input == null || input.trim().isEmpty()) {
        return cb.conjunction();
      }

      String trimmedInput = input.trim().toLowerCase(Locale.ROOT);

      Expression<String> firstLast = cb.concat(cb.lower(root.get("firstName")), cb.literal(" "));
      firstLast = cb.concat(firstLast, cb.lower(root.get("lastName")));

      Expression<String> lastFirst = cb.concat(cb.lower(root.get("lastName")), cb.literal(" "));
      lastFirst = cb.concat(lastFirst, cb.lower(root.get("firstName")));

      Predicate matchFirstLast = cb.like(firstLast, trimmedInput + "%");
      Predicate matchLastFirst = cb.like(lastFirst, trimmedInput + "%");

      return cb.or(matchFirstLast, matchLastFirst);
    };
  }

  public Specification<TeacherEntity> nameMatchesPrefixForTeacher(String input) {
    return (Root<TeacherEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      if (input == null || input.trim().isEmpty()) {
        return cb.conjunction();
      }

      String trimmedInput = input.trim().toLowerCase(Locale.ROOT);

      Expression<String> firstLast = cb.concat(cb.lower(root.get("firstName")), cb.literal(" "));
      firstLast = cb.concat(firstLast, cb.lower(root.get("lastName")));

      Expression<String> lastFirst = cb.concat(cb.lower(root.get("lastName")), cb.literal(" "));
      lastFirst = cb.concat(lastFirst, cb.lower(root.get("firstName")));

      Predicate matchFirstLast = cb.like(firstLast, trimmedInput + "%");
      Predicate matchLastFirst = cb.like(lastFirst, trimmedInput + "%");

      return cb.or(matchFirstLast, matchLastFirst);
    };
  }
}
