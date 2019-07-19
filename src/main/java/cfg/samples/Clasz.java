package cfg.samples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Clasz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String code;
	private String title;
	private String description;
}
