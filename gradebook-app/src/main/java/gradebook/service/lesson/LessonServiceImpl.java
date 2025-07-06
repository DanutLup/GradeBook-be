package gradebook.service.lesson;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.dto.response.lesson.LessonResponseDto;
import gradebook.exception.LessonNotFoundException;
import gradebook.exception.TeacherUnauthorizedException;
import gradebook.exception.UserNotFoundException;
import gradebook.repository.db.CourseRepository;
import gradebook.repository.db.LessonRepository;
import gradebook.repository.db.TeacherRepository;
import gradebook.repository.db.data.CourseEntity;
import gradebook.repository.db.data.LessonEntity;
import gradebook.repository.db.data.TeacherEntity;
import java.util.List;
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
    validateTeachersRights(createLessonRequestDto);

    LessonEntity lessonEntity = LessonMapper.mapToLessonEntity(createLessonRequestDto);
    CourseEntity course =
        courseRepository
            .findById(createLessonRequestDto.getCourseId())
            .orElseThrow(() -> new LessonNotFoundException("Cursul nu a fost găsit"));
    lessonEntity.setCourse(course);

    lessonRepository.saveAndFlush(lessonEntity);
  }

  @Transactional
  @Override
  public void updateLesson(Integer lessonId, UpdateLessonRequestDto updateLessonRequestDto) {
    LessonEntity lessonEntity =
        lessonRepository
            .findById(lessonId)
            .orElseThrow(() -> new LessonNotFoundException("Lecția nu a fost găsită"));
    LessonMapper.mapToLessonEntity(lessonEntity, updateLessonRequestDto);
    lessonRepository.saveAndFlush(lessonEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public List<LessonResponseDto> getLessons(Integer courseId) {
    List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);

    return LessonMapper.mapToLessonsResponse(lessons);
  }

  @Override
  @Transactional
  public void deleteLesson(Integer lessonId) {
    lessonRepository.deleteById(lessonId);
  }

  @Override
  public LessonResponseDto getLesson(Integer lessonId) {
    LessonEntity lessonEntity = lessonRepository.findById(lessonId).orElseThrow();
    return LessonMapper.mapToLessonResponseDto(lessonEntity);
  }

  private boolean isTeacherOwnerOfTheCourse(Integer requestCourseId, TeacherEntity teacherEntity) {
    return teacherEntity.getCourses().stream()
        .map(CourseEntity::getId)
        .anyMatch(courseId -> courseId.equals(requestCourseId));
  }

  private void validateTeachersRights(CreateLessonRequestDto createLessonRequestDto) {
    TeacherEntity teacherEntity =
        teacherRepository
            .findById(createLessonRequestDto.getTeacherId())
            .orElseThrow(() -> new UserNotFoundException("Profesorul nu a fost găsit"));
    if (!isTeacherOwnerOfTheCourse(createLessonRequestDto.getCourseId(), teacherEntity)) {
      throw new TeacherUnauthorizedException("Profesorul nu are dreptul de a crea lecții la acest curs");
    }
  }
}
