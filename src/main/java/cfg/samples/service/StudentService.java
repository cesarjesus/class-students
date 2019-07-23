package cfg.samples.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cfg.samples.domain.Course;
import cfg.samples.domain.Student;
import cfg.samples.repository.CourseRepository;
import cfg.samples.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Optional<Student> findById(Long id) {
		return studentRepository.findById(id);
	}
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public Student update(Long id, Student student) {
		return studentRepository.findById(id)
			.map(st -> {
				st.setId(id);
				st.setFirstName(student.getFirstName());
				st.setLastName(student.getLastName());
				return studentRepository.save(st);
			})
			.get();
	}

	public void deleteById(Long id) {
		studentRepository.deleteById(id);
	}

	public List<String> findAllEnrollments(Long id) {
		return studentRepository.findAllEnrollmentsForStudent(id);
	}

	public Optional<String> findEnrollmentForStudent(Long id, String code) {
		return studentRepository.findEnrollmentForStudent(id, code);
	}

	public void addEnrollmentForStudent(Long id, String code) {
		Optional<Student> student = studentRepository.findById(id);
		Optional<Course> course = courseRepository.findById(code);
		if (student.isPresent() && course.isPresent()) {
			student.get().addEnrollment(course.get());
			studentRepository.save(student.get());
		}
	}
	
	public void removeEnrollmentForStudent(Long id, String code) {
		Optional<Student> student = studentRepository.findById(id);
		Optional<Course> course = courseRepository.findById(code);
		if (student.isPresent() && course.isPresent()) {
			student.get().removeEnrollment(course.get());
			studentRepository.save(student.get());
		}
	}
}
