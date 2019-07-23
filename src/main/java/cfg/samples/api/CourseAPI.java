package cfg.samples.api;

import java.util.List;
import java.util.Optional;

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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/class")
public class CourseAPI {

	private final CourseService courseService;
	
	@GetMapping
	public ResponseEntity<List<Course>> findAll() {
		return ResponseEntity.ok(courseService.findAll());
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Course> findById(@PathVariable String code) {
		Optional<Course> course = courseService.findById(code);
		if (!course.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(course.get());
	}
	
	@PostMapping
	public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
		return ResponseEntity.ok(courseService.save(course));
	}
	
	@PutMapping("/{code}")
	public ResponseEntity<Course> update(@PathVariable String code, @Valid @RequestBody Course course) {
		Optional<Course> cl = courseService.findById(code);
		if (!cl.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(courseService.update(code, course));
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{code}")
	public ResponseEntity delete(@PathVariable String code) {
		Optional<Course> cl = courseService.findById(code);
		if (!cl.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		courseService.deleteById(code);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{code}/enrolls")
	public ResponseEntity<List<Long>> findAllEnrollsByCode(@PathVariable String code) {
		Optional<Course> course = courseService.findById(code);
		if (!course.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		List<Long> enrolls = courseService.findAllEnrollsByCode(code);
		return ResponseEntity.ok(enrolls);
	}
}
