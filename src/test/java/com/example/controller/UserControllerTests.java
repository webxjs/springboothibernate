package com.example.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.HibernateExampleApplication;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = HibernateExampleApplication.class)

public class UserControllerTests {
    
	@Autowired
    private MockMvc mvc;

    @Test
    public void testUsers_hibernate_ex() throws Exception {
    	this.mvc.perform(get("/hibernate/ex")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("123")));
    }
    
    @Test
    public void testUsers_hibernate_users() throws Exception {
    	this.mvc.perform(get("/hibernate/users")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("123")));
    }
}
