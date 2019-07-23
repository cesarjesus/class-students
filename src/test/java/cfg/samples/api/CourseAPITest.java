package cfg.samples.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import cfg.samples.domain.Course;
import cfg.samples.service.CourseService;
import cfg.samples.service.exceptions.CourseNotFoundException;

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
	public void findAllEmptyTest() throws Exception {
		when(courseService.findAll()).thenReturn(new ArrayList<Course>());
		mockMvc.perform(get("/api/v1/course"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}
	
	@Test
	public void findAllTest() throws Exception {
		when(courseService.findAll()).thenReturn(courses);
		mockMvc.perform(get("/api/v1/course"))
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"code\":\"001C\",\"title\":\"Physics\",\"description\":\"An Introduction\"},{\"code\":\"1B1C\",\"title\":\"Math\",\"description\":\"What Math is?\"},{\"code\":\"001A\",\"title\":\"Geo\",\"description\":\"An Introduction to Geo\"}]"));
	}
	
	@Test
	public void findByCodeTest() throws Exception {
		when(courseService.findById("1B1C")).thenReturn(courses.get(1));
		mockMvc.perform(get("/api/v1/course/1B1C"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"code\":\"1B1C\",\"title\":\"Math\",\"description\":\"What Math is?\"}"));
	}

	@Test
	public void findByCodeNotFoundTest() throws Exception {
		when(courseService.findById("X1Z1")).thenThrow(CourseNotFoundException.class);
		mockMvc.perform(get("/api/v1/course/X1Z1"))
			.andExpect(status().isNotFound());
	}
}
