package cfg.samples.api;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import cfg.samples.domain.Student;
import cfg.samples.service.StudentService;
import cfg.samples.service.exceptions.StudentNotFoundException;

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
	public void findAllTestEmpty() throws Exception {
		when(studentService.findAll()).thenReturn(new ArrayList<Student>());
		mockMvc.perform(get("/api/v1/students"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}
	
	@Test
	public void findAllTestElements() throws Exception {
		when(studentService.findAll()).thenReturn(students);
		mockMvc.perform(get("/api/v1/students"))
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"id\":1,\"firstName\":\"Jhon\",\"lastName\":\"Proctor\"},{\"id\":2,\"firstName\":\"Pedro\",\"lastName\":\"Picapiedra\"},{\"id\":26,\"firstName\":\"Ernesto\",\"lastName\":\"Piero\"}]"));
	}
	
	@Test
	public void findByIdTest() throws Exception {
		when(studentService.findById(26L)).thenReturn(students.get(2));
		mockMvc.perform(get("/api/v1/students/26"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"id\":26,\"firstName\":\"Ernesto\",\"lastName\":\"Piero\"}"));
		
		when(studentService.findById(18L)).thenThrow(StudentNotFoundException.class);
		mockMvc.perform(get("/api/v1/students/18"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdTestNotFound() throws Exception {
		when(studentService.findById(18L)).thenThrow(StudentNotFoundException.class);
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

		when(studentService.update(2L, st)).thenReturn(st);
		mockMvc.perform(put("/api/v1/students/2")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"firstName\": \"Pedro\", \"lastName\": \"Sarmiento\"}"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"id\":2, \"firstName\": \"Pedro\", \"lastName\": \"Sarmiento\"}"));
	}
	
	//@Test
	public void updateTestNotFound() throws Exception {
		Student st = new Student();
		st.setFirstName("Pedro");
		st.setLastName("Sarmiento");

		when(studentService.update(33L, st)).thenThrow(StudentNotFoundException.class);
		mockMvc.perform(put("/api/v1/students/33")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"firstName\": \"Pedro\", \"lastName\": \"Sarmiento\"}"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteTest() throws Exception {
		mockMvc.perform(delete("/api/v1/students/1"))
			.andExpect(status().isOk());
		verify(studentService).deleteById(1L);
	}
	
	@Test
	public void deleteNotFoundTest() throws Exception {
		doThrow(StudentNotFoundException.class).when(studentService).deleteById(11L);
		mockMvc.perform(delete("/api/v1/students/11"))
			.andExpect(status().isNotFound());
	}
}
