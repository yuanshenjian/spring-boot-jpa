package org.yood.springboot.jpa.web.controller;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.yood.springboot.jpa.BasicMockMvcTest;
import org.yood.springboot.jpa.entity.User;
import org.yood.springboot.jpa.service.UserService;
import org.yood.springboot.jpa.util.JSONUtils;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BasicMockMvcTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Override
    public Object injectController() {
        return userController;
    }

    @Test
    public void testGetAll() throws Exception {
        User user1 = new User("ShenjianYuan1");
        User user2 = new User("ShenjianYuan2");
        when(userService.getAll()).thenReturn(Arrays.asList(user1, user2));
        mockGet("/users", MediaType.APPLICATION_JSON).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("ShenjianYuan1")))
                .andExpect(jsonPath("$[1].name", is("ShenjianYuan2")));
    }

    @Test
    public void testAdd() throws Exception {
        User user = new User("Shenjian,Yuan");
        user.setSex(User.Sex.MALE);
        mockPost("/users", MediaType.APPLICATION_JSON, JSONUtils.toJSONString(user)).andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("Shenjian,Yuan");
        mockPut("/users", MediaType.APPLICATION_JSON, JSONUtils.toJSONString(user)).andExpect(status().isOk());
        verify(userService,times(1)).update(any(User.class));
    }
}