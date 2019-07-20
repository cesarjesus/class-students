package cfg.samples;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query(value = "SELECT * FROM clasz cls "
			+ "INNER JOIN enrollemnt enr on enr.class_code = clasz.code "
			+ "INNER JOIN student st on st.id = enr.student_id "
			+ "WHERE st.id = :studentId AND cls.code = :claszCode")
	Optional<Clasz> findEnrollmentForStudent(@Param("studentId") Long studentId,
			@Param("claszCode") String claszCode);
}
