package cfg.samples.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
		},
		mappedBy = "claszs")
	private Set<Student> students;
	
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
