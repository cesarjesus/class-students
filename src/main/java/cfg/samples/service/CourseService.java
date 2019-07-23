package cfg.samples.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cfg.samples.domain.Course;
import cfg.samples.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	
	public List<Course> findAll() {
		return courseRepository.findAll();
	}
	
	public Optional<Course> findById(String code) {
		return courseRepository.findById(code);
	}
	
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	public Course update(String code, Course course) {
		return courseRepository.findById(code)
				.map(cl -> {
					cl.setCode(code);
					cl.setTitle(course.getTitle());
					cl.setDescription(course.getDescription());
					return courseRepository.save(cl);
				})
				.get();
	}

	public void deleteById(String code) {
		courseRepository.deleteById(code);
	}
	
	public List<Long> findAllEnrollsByCode(String code) {
		return courseRepository.findAllEnrollsByCode(code);
	}
}
