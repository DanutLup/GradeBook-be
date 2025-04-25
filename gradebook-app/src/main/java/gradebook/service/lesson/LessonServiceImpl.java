package gradebook.service.lesson;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.exception.LessonNotFoundException;
import gradebook.exception.TeacherUnauthorizedException;
import gradebook.exception.UserNotFoundException;
import gradebook.repository.db.CourseRepository;
import gradebook.repository.db.LessonRepository;
import gradebook.repository.db.TeacherRepository;
import gradebook.repository.db.data.CourseEntity;
import gradebook.repository.db.data.LessonEntity;
import gradebook.repository.db.data.TeacherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
  private final LessonRepository lessonRepository;
  private final CourseRepository courseRepository;
  private final TeacherRepository teacherRepository;

  @Override
  @Transactional
  public void createLesson(CreateLessonRequestDto createLessonRequestDto) {
    TeacherEntity teacherEntity =
        teacherRepository
            .findById(createLessonRequestDto.getTeacherId())
            .orElseThrow(() -> new UserNotFoundException("Teacher not found"));
    if (!isTeacherOwnerOfTheCourse(createLessonRequestDto.getCourseId(), teacherEntity)) {
      throw new TeacherUnauthorizedException("Teacher is not owner of this course");
    }
    LessonEntity lessonEntity = LessonMapper.mapToLessonEntity(createLessonRequestDto);
    CourseEntity course =
        courseRepository
            .findById(createLessonRequestDto.getCourseId())
            .orElseThrow(() -> new LessonNotFoundException("Course not found"));
    lessonEntity.setCourse(course);
    lessonRepository.saveAndFlush(lessonEntity);
  }

  @Transactional
  @Override
  public void updateLesson(UpdateLessonRequestDto updateLessonRequestDto) {
    LessonEntity lessonEntity = LessonMapper.mapToLessonEntity(updateLessonRequestDto);
    CourseEntity course =
        courseRepository
            .findById(updateLessonRequestDto.getCourseId())
            .orElseThrow(() -> new LessonNotFoundException("Course not found"));
    lessonEntity.setCourse(course);
    lessonRepository.saveAndFlush(lessonEntity);
  }

  private boolean isTeacherOwnerOfTheCourse(Integer requestCourseId, TeacherEntity teacherEntity) {
    return teacherEntity.getCourses().stream()
        .map(CourseEntity::getId)
        .anyMatch(courseId -> courseId.equals(requestCourseId));
  }
}
