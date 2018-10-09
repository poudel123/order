package com.capgemini.ordersapp;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.capgemini.ordersapp.controller.OrderController;
import com.capgemini.ordersapp.entity.Orders;
import com.capgemini.ordersapp.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderControllerTest 
{
	@Mock
	OrderService orderService;
	
	@InjectMocks
	OrderController orderController;
	private MockMvc mockMvc;
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}
	
	@Test
	public void testAddOrders() throws Exception
	{
		when(orderService.addOrder(Mockito.isA(Orders.class))).thenReturn(new Orders(1234,"toy"));
		mockMvc.perform(post("/order")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"  \"orderId\": \"1234\",\r\n" + 
						"  \"orderName\": \"toy\"\r\n" + 
						"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId").value(1234))
				.andDo(print());
		
	}
	
	@Test 
	public void testGetOrdersbyId() throws Exception {
		when(orderService.getOrderById(1234)).thenReturn(new Orders(1234,"toy"));
		mockMvc.perform(get("/order/1234"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.orderId").exists())
		.andExpect(jsonPath("$.orderName").exists())
		.andExpect(jsonPath("$.orderId").value("1234"))
		.andExpect(jsonPath("$.orderName").value("toy"))
	.andDo(print());
		   
	}
	
     @Test
	public void testDeleteOrderbyId() throws Exception {
    	// when(orderService.getOrderById(1234)).thenReturn(new Orders(1234,"toy"));
		mockMvc.perform(delete("/order/1234").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print());
	}
}
