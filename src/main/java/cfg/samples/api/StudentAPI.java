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

import cfg.samples.domain.Student;
import cfg.samples.service.StudentService;
import cfg.samples.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/students")
public class StudentAPI {

	private final StudentService studentService;
	
	@GetMapping
	public ResponseEntity<List<Student>> findAll() {
		return ResponseEntity.ok(studentService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> findById(@PathVariable Long id) {
		Student student;
		try {
			student = studentService.findById(id);
		} catch (StudentNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(student);
	}
	
	@PostMapping
	public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
		return ResponseEntity.ok(studentService.save(student));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Student> update(@PathVariable Long id, @Valid @RequestBody Student student) {
		Student st;
		try {
			st = studentService.update(id, student);
		} catch (StudentNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(st);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		try {
			studentService.deleteById(id);
		} catch (StudentNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}/enrolls")
	public ResponseEntity<List<String>> findAllEnrolls(@PathVariable Long id) {
		List<String> enrolls;
		try {
			enrolls = studentService.findAllEnrolls(id);
		} catch (StudentNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(enrolls);
	}
}