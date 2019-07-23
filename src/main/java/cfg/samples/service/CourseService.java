package cfg.samples.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cfg.samples.domain.Course;
import cfg.samples.domain.Student;
import cfg.samples.repository.CourseRepository;
import cfg.samples.repository.StudentRepository;
import cfg.samples.service.exceptions.CourseNotFoundException;
import cfg.samples.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	
	public List<Course> findAll() {
		return courseRepository.findAll();
	}
	
	public Course findById(String code) throws CourseNotFoundException {
		Optional<Course> course = courseRepository.findById(code);
		if (!course.isPresent()) {
			throw new CourseNotFoundException();
		}

		return course.get();
	}
	
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	public Course update(String code, Course course) throws CourseNotFoundException {
		Course co = this.findById(code);
		co.setTitle(course.getTitle());
		co.setDescription(course.getDescription());
		return courseRepository.save(co);
	}

	public void deleteById(String code) throws CourseNotFoundException {
		this.findById(code);
		courseRepository.deleteById(code);
	}
	
	public List<Long> findAllEnrollmentsByCode(String code) throws CourseNotFoundException {
		this.findById(code);
		return courseRepository.findAllEnrollmentsByCode(code);
	}
	
	public void addEnrollmentForStudent(String code, Long id) throws StudentNotFoundException,
																CourseNotFoundException {
		Course course = this.findById(code);
		Optional<Student> student = studentRepository.findById(id);
		
		if (!student.isPresent()) {
			throw new StudentNotFoundException();
		}

		course.addEnrollment(student.get());
		courseRepository.save(course);
	}
	
	public void removeEnrollmentForStudent(String code, Long id) throws StudentNotFoundException,
																	CourseNotFoundException {
		Course course = this.findById(code);
		Optional<Student> student = studentRepository.findById(id);
		
		if (!student.isPresent()) {
			throw new StudentNotFoundException();
		}
		
		course.removeEnrollment(student.get());
		courseRepository.save(course);
	}
}
