package cfg.samples.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cfg.samples.domain.Student;
import cfg.samples.repository.StudentRepository;
import cfg.samples.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Student findById(Long id) throws StudentNotFoundException {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent()) {
			throw new StudentNotFoundException();
		}
		
		return student.get();
	}
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public Student update(Long id, Student student) throws StudentNotFoundException {
		Student st = this.findById(id);
		st.setFirstName(student.getFirstName());
		st.setLastName(student.getLastName());
		return studentRepository.save(st);
	}

	public void deleteById(Long id) throws StudentNotFoundException {
		this.findById(id);
		studentRepository.deleteById(id);
	}

	public List<String> findAllEnrolls(Long id) throws StudentNotFoundException {
		this.findById(id);
		return studentRepository.findAllEnrollsForStudent(id);
	}
}
