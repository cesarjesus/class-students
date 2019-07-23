package cfg.samples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cfg.samples.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query(value = "SELECT co.code FROM course co "
			+ "INNER JOIN enrollment enr on enr.course_code = co.code "
			+ "WHERE enr.student_id = :id",
			nativeQuery = true)
	List<String> findAllEnrollsForStudent(@Param("id") Long id);
}
