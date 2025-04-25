package gradebook.exception;

public class TeacherUnauthorizedException extends RuntimeException {
  public TeacherUnauthorizedException(String message) {
    super(message);
  }
}
