package cfg.samples.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	}, mappedBy = "students")
	private Set<Course> courses;

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
