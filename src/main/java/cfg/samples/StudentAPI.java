package cfg.samples;

import java.util.ArrayList;
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
		Optional<Student> student = studentService.findById(id);
		if (!student.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(student.get());
	}
	
	@PostMapping
	public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
		return ResponseEntity.ok(studentService.save(student));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Student> update(@PathVariable Long id, @Valid @RequestBody Student student) {
		Optional<Student> st = studentService.findById(id);
		if (!st.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(studentService.save(student));
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		Optional<Student> student = studentService.findById(id);
		if (!student.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		studentService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}/enrollment")
	public ResponseEntity<List<Clasz>> findAllEnrollment(@PathVariable Long id) {
		Optional<Student> student = studentService.findById(id);
		if (!student.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		
		List<Clasz> enrollments = new ArrayList<Clasz>(student.get().getClaszs());
		return ResponseEntity.ok(enrollments);
	}
	
	@GetMapping("/{id}/enrollment/{code}")
	public ResponseEntity<Clasz> findEnrollment(@PathVariable Long id, @PathVariable String code) {
		Optional<Student> student = studentService.findById(id);
		if (!student.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		
		Optional<Clasz> enrollment = studentService.findEnrollmentForStudent(id, code);
		if (!enrollment.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(enrollment.get());
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/enrollment/{code}")
	public ResponseEntity addEnrollment(@PathVariable Long id, @PathVariable String code) {
		Optional<Student> student = studentService.findById(id);
		if (!student.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		
		studentService.addEnrollmentForStudent(id, code);
		return ResponseEntity.ok().build();
	}
}