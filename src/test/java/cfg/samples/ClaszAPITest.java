package cfg.samples;

import static org.junit.Assert.*;
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
@WebMvcTest(value = ClaszAPI.class)
public class ClaszAPITest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClaszService claszService;
	
	private List<Clasz> claszs;
	
	@Before
	public void setup() {
		claszs = new ArrayList<Clasz>();
		Clasz cl = new Clasz();
		cl.setCode("001C");
		cl.setTitle("Physics");
		cl.setDescription("An Introduction");
		claszs.add(cl);
		
		cl = new Clasz();
		cl.setCode("1B1C");
		cl.setTitle("Math");
		cl.setDescription("What Math is?");
		claszs.add(cl);
		
		cl = new Clasz();
		cl.setCode("001A");
		cl.setTitle("Geo");
		cl.setDescription("An Introduction to Geo");
		claszs.add(cl);
	}
	
	@Test
	public void findAllTest( ) throws Exception {
		when(claszService.findAll()).thenReturn(new ArrayList<Clasz>());
		mockMvc.perform(get("/api/v1/class"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
		
		when(claszService.findAll()).thenReturn(claszs);
		mockMvc.perform(get("/api/v1/class"))
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"code\":\"001C\",\"title\":\"Physics\",\"description\":\"An Introduction\"},{\"code\":\"1B1C\",\"title\":\"Math\",\"description\":\"What Math is?\"},{\"code\":\"001A\",\"title\":\"Geo\",\"description\":\"An Introduction to Geo\"}]"));
	}
	
	@Test
	public void testfindByCode() throws Exception {
		when(claszService.findById("1B1C")).thenReturn(Optional.of(claszs.get(1)));
		mockMvc.perform(get("/api/v1/class/1B1C"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"code\":\"1B1C\",\"title\":\"Math\",\"description\":\"What Math is?\"}"));
	}

}
