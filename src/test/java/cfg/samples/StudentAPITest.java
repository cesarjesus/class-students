package cfg.samples;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentAPI.class)
public class StudentAPITest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;

	private List<Student> students;
	
	@Before
	public void setup() throws Exception {
		students = new ArrayList<Student>();
		Student student = new Student();
		student.setId(1L);
		student.setFirstName("Jhon");
		student.setLastName("Proctor");
		students.add(student);
		
		student = new Student();
		student.setId(2L);
		student.setFirstName("Pedro");
		student.setLastName("Picapiedra");
		students.add(student);
		
		student = new Student();
		student.setId(26L);
		student.setFirstName("Ernesto");
		student.setLastName("Piero");
		students.add(student);
	}
	
	@Test
	public void findAllTest() throws Exception {
		when(studentService.findAll()).thenReturn(new ArrayList<Student>());
		mockMvc.perform(get("/api/v1/students"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
		
		when(studentService.findAll()).thenReturn(students);
		mockMvc.perform(get("/api/v1/students"))
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"id\":1,\"firstName\":\"Jhon\",\"lastName\":\"Proctor\"},{\"id\":2,\"firstName\":\"Pedro\",\"lastName\":\"Picapiedra\"},{\"id\":26,\"firstName\":\"Ernesto\",\"lastName\":\"Piero\"}]"));
	}
	
	@Test
	public void findByIdTest() throws Exception {
		when(studentService.findById(26L)).thenReturn(Optional.of(students.get(2)));
		mockMvc.perform(get("/api/v1/students/26"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"id\":26,\"firstName\":\"Ernesto\",\"lastName\":\"Piero\"}"));
	}

}
