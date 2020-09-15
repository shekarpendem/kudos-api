package com.osi.kudos.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.osi.kudos.app.Application;
import com.osi.kudos.app.controller.EmployeeController;
import com.osi.kudos.app.model.Employee;
import com.osi.kudos.app.model.Kudo;
import com.osi.kudos.app.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeServiceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	String exampleEmployee = "{\"id\":1,\"firstName\":\"fName\",\"lastName\":\"lName\",\"emailId\":\"email@test.com\"}";

	
	@Test
	public void createEmployee() throws Exception {
		Employee mockEmployee = new Employee(6, "fName", "lName", "email@test.com");
		Mockito.when(
				employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(mockEmployee);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/employees")
				.accept(MediaType.APPLICATION_JSON).content(exampleEmployee)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	
	
}
