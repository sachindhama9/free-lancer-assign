package com.tadaah.freelancer.dao;

import com.tadaah.freelancer.enums.FreeLancerStatus;
import com.tadaah.freelancer.model.FreeLancer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FreeLancerRepositoryTest {

    @Mock
    private FreeLancerRepository freelancerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findFreelancerById_ShouldReturnFreelancer_WhenFreelancerExists() {
        // Arrange
        FreeLancer freelancer = new FreeLancer(1L, "Sachin", "Dhama", LocalDate.now() , "Male" , FreeLancerStatus.NEW_FREELANCER, LocalDateTime.of(2005,6,1,0,0));
        when(freelancerRepository.findById(1L)).thenReturn(Optional.of(freelancer));

        // Act
        Optional<FreeLancer> result = freelancerRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Sachin", result.get().getFirstName());
    }

    @Test
    public void findFreelancerById_ShouldReturnEmpty_WhenFreelancerDoesNotExist() {
        // Arrange
        when(freelancerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<FreeLancer> result = freelancerRepository.findById(1L);

        // Assert
        assertFalse(result.isPresent());
    }
}

