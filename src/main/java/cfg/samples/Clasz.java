package cfg.samples;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Clasz {

	@Id
	private String code;
	private String title;
	private String description;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "claszs")
	private Set<Student> students;
}
