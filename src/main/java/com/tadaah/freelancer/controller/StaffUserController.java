package com.tadaah.freelancer.controller;

import com.tadaah.freelancer.dao.FreeLancerRepository;
import com.tadaah.freelancer.enums.FreeLancerStatus;
import com.tadaah.freelancer.exceptions.ResourceNotFoundException;
import com.tadaah.freelancer.model.FreeLancer;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffUserController {

    private final FreeLancerRepository freelancerRepository;

    @ApiOperation(value = "Get all Free Lancers", response = FreeLancer.class)
    @GetMapping("/freelancers/new")
    public List<FreeLancer> getNewFreelancers() {
        return freelancerRepository.findByStatus(FreeLancerStatus.NEW_FREELANCER);
    }

    @ApiOperation(value = "Verify a FreeLancer", response = FreeLancer.class)
    @PutMapping("/freelancers/{id}/verify")
    public ResponseEntity<FreeLancer> verifyFreelancer(@PathVariable Long id) {
        FreeLancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id: " + id));
        freelancer.setStatus(FreeLancerStatus.VERIFIED);
        freelancerRepository.save(freelancer);
        return ResponseEntity.ok(freelancer);
    }

    @ApiOperation(value = "Fetch all deleted Freelancers in the last 7 days", response = List.class)
    @GetMapping("/freelancers/deleted")
    public List<FreeLancer> getDeletedFreelancers() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);
        return freelancerRepository.findAllDeletedFreelancersInLast7Days(startDate, endDate);
    }
}
