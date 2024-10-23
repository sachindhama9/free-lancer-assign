package com.tadaah.freelancer.service;

import com.tadaah.freelancer.dao.FreeLancerRepository;
import com.tadaah.freelancer.enums.FreeLancerStatus;
import com.tadaah.freelancer.exceptions.ResourceNotFoundException;
import com.tadaah.freelancer.model.FreeLancer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FreeLancerServiceTest {

    @Mock
    private FreeLancerRepository freelancerRepository;

    @InjectMocks
    private FreeLancerService freelancerService;

    @Mock
    private KafkaTemplate<String, FreeLancer> kafkaTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createFreelancer_ShouldSaveFreelancer() {
        // Arrange
        FreeLancer freelancer = new FreeLancer(1L, "John", "Doe", LocalDate.now() , "Male" , FreeLancerStatus.NEW_FREELANCER, LocalDateTime.of(2005,6,1,0,0));
        when(freelancerRepository.save(any(FreeLancer.class))).thenReturn(freelancer);

        // Act
        FreeLancer result = freelancerService.createFreelancer(freelancer);

        // Verify that the kafkaTemplate sends the correct message
        ArgumentCaptor<FreeLancer> argumentCaptor = ArgumentCaptor.forClass(FreeLancer.class);
        verify(kafkaTemplate, times(1)).send(eq("freelancer-topic"), argumentCaptor.capture());


        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateFreelancer_ShouldThrowException_WhenFreelancerNotFound() {
        // Arrange
        FreeLancer updatedFreelancer = new FreeLancer(1L, "Sachin", "Dhama", LocalDate.now() , "Male" , FreeLancerStatus.NEW_FREELANCER, LocalDateTime.of(2005,6,1,0,0));
        when(freelancerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        freelancerService.updateFreelancer(1L, updatedFreelancer);
    }

    @Test
    public void deleteFreelancer_ShouldDelete_WhenFreelancerExists() {
        // Arrange
        FreeLancer freelancer = new FreeLancer(1L, "Sachin", "Dhama", LocalDate.now() , "Male" , FreeLancerStatus.NEW_FREELANCER, LocalDateTime.of(2005,6,1,0,0));
        when(freelancerRepository.findById(1L)).thenReturn(Optional.of(freelancer));

        // Act
        freelancerService.markForDeletion(1L);

        // Verify that the kafkaTemplate is called
        ArgumentCaptor<FreeLancer> argumentCaptor = ArgumentCaptor.forClass(FreeLancer.class);
        verify(kafkaTemplate, times(1)).send(eq("freelancer-topic"), argumentCaptor.capture());

        // Assert
        verify(freelancerRepository, times(1)).save(freelancer);

    }
}

