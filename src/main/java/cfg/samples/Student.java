package cfg.samples;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
	})
	@JoinTable(name = "enrollment", 
		joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name ="class_code", referencedColumnName = "code"))
	private Set<Clasz> claszs;
	
	public void addEnrollment(Clasz clasz) {
		claszs.add(clasz);
		clasz.getStudents().add(this);
	}

	public void removeEnrollment(Clasz clasz) {
		claszs.remove(clasz);
		clasz.getStudents().remove(this);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Student)) {
			return false;
		}
		
		return id != null && id.equals(((Student)o).getId());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
