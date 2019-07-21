package cfg.samples;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final ClaszRepository claszRepository;
	
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
		Optional<Clasz> clasz = claszRepository.findById(code);
		if (student.isPresent() && clasz.isPresent()) {
			student.get().addEnrollment(clasz.get());
			studentRepository.save(student.get());
		}
	}
}
