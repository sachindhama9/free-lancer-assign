package com.tadaah.freelancer.controller;

import com.tadaah.freelancer.enums.FreeLancerStatus;
import com.tadaah.freelancer.model.FreeLancer;
import com.tadaah.freelancer.service.FreeLancerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class FreeLancerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FreeLancerController freelancerController;

    @Mock
    private FreeLancerService freelancerService;

    private FreeLancer freelancer;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(freelancerController).build();
        freelancer = new FreeLancer(1L, "Sachin", "Dhama", LocalDate.now() , "Male" , FreeLancerStatus.NEW_FREELANCER, LocalDateTime.of(2005,6,1,0,0));
    }

    @Test
    public void createFreelancer_ShouldReturnCreatedFreelancer() throws Exception {
        // Arrange
        when(freelancerService.createFreelancer(any(FreeLancer.class))).thenReturn(freelancer);

        // Act & Assert
        mockMvc.perform(post("/api/freelancers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\": \"Sachin\", \"lastName\": \"Dhama\", \"gender\": \"Male\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sachin"))
                .andExpect(jsonPath("$.lastName").value("Dhama"));
    }

    @Test
    public void updateFreelancer_ShouldReturnUpdatedFreelancer() throws Exception {
        // Arrange
        when(freelancerService.updateFreelancer(any(Long.class), any(FreeLancer.class))).thenReturn(freelancer);

        // Act & Assert
        mockMvc.perform(put("/api/freelancers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\": \"Sachin\", \"lastName\": \"Dhama\", \"gender\": \"Male\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sachin"))
                .andExpect(jsonPath("$.lastName").value("Dhama"));
    }

    @Test
    public void deleteFreelancer_ShouldReturnNoContent() throws Exception {
        // Arrange
        Mockito.doNothing().when(freelancerService).markForDeletion(any(Long.class));

        // Act & Assert
        mockMvc.perform(delete("/api/freelancers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

