package cfg.samples.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Course {

	@Id
	private String code;
	private String title;
	private String description;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
		cascade = {
				CascadeType.PERSIST,
				CascadeType.MERGE
	})
	@JoinTable(name = "enrollment", 
		joinColumns = @JoinColumn(name = "course_code", referencedColumnName = "code"), 
		inverseJoinColumns = @JoinColumn(name ="student_id", referencedColumnName = "id"))
	private Set<Student> students;
	
	public void addEnrollment(Student student) {
		this.students.add(student);
		student.getCourses().add(this);
	}
	
	public void removeEnrollment(Student student) {
		this.students.remove(student);
		student.getCourses().remove(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Course)) {
			return false;
		}
		
		return code != null && code.equals(((Course)o).getCode());
	}
	
	@Override
	public int hashCode() {
		return code.hashCode();
	}
}
