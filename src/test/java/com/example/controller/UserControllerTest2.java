package com.example.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.HibernateExampleApplication;
import com.example.model.User;
import com.example.service.UserService;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HibernateExampleApplication.class)

public class UserControllerTest2 {
	@Mock
    private UserService userService;
	
	@InjectMocks
    UserController userController;
	
    private MockMvc mvc;
    
    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testUsers_hibernate_users() throws Exception {
    	when(userService.getUsers()).thenReturn(new ArrayList<User>() {{
    		add(new User() {{
    			id = "id";
    			name = "xyz";
    		}});
    	}});
    	this.mvc.perform(get("/hibernate/users")).andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.size()", is(1)))
    	.andExpect(jsonPath("$[0].name", is("xyz")))
    	.andExpect(jsonPath("$[0].id", is("id")));
    }
}
