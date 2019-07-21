package cfg.samples;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.http.MediaType;
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
		
		when(studentService.findById(18L)).thenReturn(Optional.<Student>empty());
		mockMvc.perform(get("/api/v1/students/18"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void createTest() throws Exception {
		Student st = new Student();
		st.setId(12L);
		st.setFirstName("Maria");
		st.setLastName("Suarez");
		
		when(studentService.save(st)).thenReturn(st);
		mockMvc.perform(post("/api/v1/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":12, \"firstName\": \"Maria\", \"lastName\": \"Suarez\"}"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"id\":12, \"firstName\": \"Maria\", \"lastName\": \"Suarez\"}"));
	}

	//@Test
	public void updateTest() throws Exception {
		Student st = new Student();
		st.setId(2L);
		st.setFirstName("Pedro");
		st.setLastName("Sarmiento");
		
		when(studentService.findById(2L)).thenReturn(Optional.of(students.get(1)));
		when(studentService.update(2L, st)).thenReturn(st);
		mockMvc.perform(put("/api/v1/students/2")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"firstName\": \"Pedro\", \"lastName\": \"Sarmiento\"}"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"id\":2, \"firstName\": \"Pedro\", \"lastName\": \"Sarmiento\"}"));
		
		mockMvc.perform(put("/api/v1/students/33")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"firstName\": \"Pedro\", \"lastName\": \"Sarmiento\"}"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteTest() throws Exception {
		when(studentService.findById(1L)).thenReturn(Optional.of(students.get(0)));
		mockMvc.perform(delete("/api/v1/students/1"))
			.andExpect(status().isOk());
		
		mockMvc.perform(delete("/api/v1/students/11"))
		.andExpect(status().isNotFound());
	}
}
