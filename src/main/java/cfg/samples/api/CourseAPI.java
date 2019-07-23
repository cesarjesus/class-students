package cfg.samples.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cfg.samples.domain.Course;
import cfg.samples.service.CourseService;
import cfg.samples.service.exceptions.CourseNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/course")
public class CourseAPI {

	private final CourseService courseService;
	
	@GetMapping
	public ResponseEntity<List<Course>> findAll() {
		return ResponseEntity.ok(courseService.findAll());
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Course> findById(@PathVariable String code) {
		Course course;
		try {
			course = courseService.findById(code);
		} catch (CourseNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(course);
	}
	
	@PostMapping
	public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
		return ResponseEntity.ok(courseService.save(course));
	}
	
	@PutMapping("/{code}")
	public ResponseEntity<Course> update(@PathVariable String code, @Valid @RequestBody Course course) {
		Course co;
		try {
			co = courseService.update(code, course);
		} catch (CourseNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(co);
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{code}")
	public ResponseEntity delete(@PathVariable String code) {
		try {
			courseService.deleteById(code);
		} catch (CourseNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{code}/enrollment")
	public ResponseEntity<List<Long>> findAllEnrollmentsByCode(@PathVariable String code) {
		List<Long> enrollments;
		try {
			enrollments = courseService.findAllEnrollmentsByCode(code);
		} catch (CourseNotFoundException cnfe) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(enrollments);
	}
}
