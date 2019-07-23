package cfg.samples;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, String> {

	@Query(value = "SELECT st.id FROM student st "
			+ "INNER JOIN enrollment enr on enr.student_id = st.id "
			+ "WHERE enr.class_code = :code",
			nativeQuery = true)
	List<Long> findAllEnrollsByCode(@Param("code") String code);
}
