package cfg.samples;

import java.util.List;
import java.util.Optional;

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
}
