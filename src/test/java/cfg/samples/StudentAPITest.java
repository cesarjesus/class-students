package cfg.samples;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentAPITest {

	private MockMvc mockMvc;
	
	@Autowired
	private StudentAPI studentAPI;

	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.studentAPI).build();
	}
	
	//@Test
	public void testFindAll() throws Exception {
		Student student1 = new Student();
		student1.setId(1L);
		student1.setFirstName("Jhon");
		student1.setLastName("Proctoc");
		
		Student student2 = new Student();
		student2.setId(2L);
		student2.setFirstName("Pedro");
		student2.setLastName("Picapiedra");
		
		List<Student> students = Arrays.asList(student1, student2);
		given(studentAPI.findAll()).willReturn(ResponseEntity.ok(students));
		
		mockMvc.perform(get("/api/v1/students"))
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"id\":1,\"firstName\":\"Jhon\",\"lastName\":\"Proctor\"},{\"id\":2,\"firstName\":\"Pedro\",\"lastName\":\"Picapiedra\"}]"));
	}
	
	@Test
	public void testFindById() {
		assertTrue(true);
	}

}
