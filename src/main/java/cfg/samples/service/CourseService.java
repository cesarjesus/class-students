package cfg.samples.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cfg.samples.domain.Course;
import cfg.samples.repository.CourseRepository;
import cfg.samples.service.exceptions.CourseNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	
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
}
