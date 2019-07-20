package cfg.samples;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Optional<Student> findById(Long id) {
		return studentRepository.findById(id);
	}
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public void deleteById(Long id) {
		studentRepository.deleteById(id);
	}
	
	public Optional<Clasz> findEnrollment(Long id, String code) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			Set<Clasz> enrollments = student.get().getClaszs();
			Optional<Clasz> enrollment = enrollments.stream().filter(
					cl -> cl.getCode().equals(code)).findAny();
			return enrollment;
		}
		
		return Optional.<Clasz>empty();
	}
}
