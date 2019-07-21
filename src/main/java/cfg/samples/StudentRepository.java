package cfg.samples;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query(value = "SELECT cls.code FROM clasz cls "
			+ "INNER JOIN enrollment enr on enr.class_code = cls.code "
			+ "INNER JOIN student st on st.id = enr.student_id "
			+ "WHERE st.id = :studentId AND cls.code = :claszCode",
			nativeQuery = true)
	Optional<String> findEnrollmentForStudent(@Param("studentId") Long studentId,
			@Param("claszCode") String claszCode);
	
	@Query(value = "SELECT cls.code FROM clasz cls "
			+ "INNER JOIN enrollment enr on enr.class_code = cls.code "
			+ "WHERE enr.student_id = :id",
			nativeQuery = true)
	List<String> findAllEnrollmentsForStudent(@Param("id") Long id);
}
