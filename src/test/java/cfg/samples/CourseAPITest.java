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

import cfg.samples.api.CourseAPI;
import cfg.samples.domain.Course;
import cfg.samples.service.CourseService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseAPI.class)
public class CourseAPITest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CourseService courseService;
	
	private List<Course> courses;
	
	@Before
	public void setup() {
		courses = new ArrayList<Course>();
		Course cl = new Course();
		cl.setCode("001C");
		cl.setTitle("Physics");
		cl.setDescription("An Introduction");
		courses.add(cl);
		
		cl = new Course();
		cl.setCode("1B1C");
		cl.setTitle("Math");
		cl.setDescription("What Math is?");
		courses.add(cl);
		
		cl = new Course();
		cl.setCode("001A");
		cl.setTitle("Geo");
		cl.setDescription("An Introduction to Geo");
		courses.add(cl);
	}
	
	@Test
	public void findAllTest( ) throws Exception {
		when(courseService.findAll()).thenReturn(new ArrayList<Course>());
		mockMvc.perform(get("/api/v1/class"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
		
		when(courseService.findAll()).thenReturn(courses);
		mockMvc.perform(get("/api/v1/class"))
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"code\":\"001C\",\"title\":\"Physics\",\"description\":\"An Introduction\"},{\"code\":\"1B1C\",\"title\":\"Math\",\"description\":\"What Math is?\"},{\"code\":\"001A\",\"title\":\"Geo\",\"description\":\"An Introduction to Geo\"}]"));
	}
	
	@Test
	public void findByCodeTest() throws Exception {
		when(courseService.findById("1B1C")).thenReturn(Optional.of(courses.get(1)));
		mockMvc.perform(get("/api/v1/class/1B1C"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"code\":\"1B1C\",\"title\":\"Math\",\"description\":\"What Math is?\"}"));
		
		when(courseService.findById("X1Z1")).thenReturn(Optional.<Course>empty());
		mockMvc.perform(get("/api/v1/class/X1Z1"))
			.andExpect(status().isNotFound());
	}

}
