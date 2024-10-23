package com.tadaah.freelancer.controller;

import com.tadaah.freelancer.model.FreeLancer;
import com.tadaah.freelancer.service.FreeLancerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Freelancer Controller")
@RestController
@RequestMapping("/api/freelancers")
@RequiredArgsConstructor
public class FreeLancerController {

    private final FreeLancerService service;

    @ApiOperation(value = "Create a new FreeLancer", response = FreeLancer.class)
    @PostMapping
    public ResponseEntity<FreeLancer> createFreelancer(@RequestBody FreeLancer freelancer) {
        return ResponseEntity.ok(service.createFreelancer(freelancer));
    }

    @ApiOperation(value = "Update an existing FreeLancer", response = FreeLancer.class)
    @PutMapping("/{id}")
    public ResponseEntity<FreeLancer> updateFreelancer(@PathVariable Long id, @RequestBody FreeLancer freelancer) {
        return ResponseEntity.ok(service.updateFreelancer(id, freelancer));
    }

    @ApiOperation(value = "Delete a Freelancer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> markForDeletion(@PathVariable Long id) {
        service.markForDeletion(id);
        return ResponseEntity.noContent().build();
    }
}
