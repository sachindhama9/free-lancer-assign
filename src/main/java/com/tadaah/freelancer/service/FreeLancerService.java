package com.tadaah.freelancer.service;

import com.tadaah.freelancer.dao.FreeLancerRepository;
import com.tadaah.freelancer.enums.FreeLancerStatus;
import com.tadaah.freelancer.exceptions.ResourceNotFoundException;
import com.tadaah.freelancer.model.FreeLancer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FreeLancerService {

    private final FreeLancerRepository repository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public FreeLancer createFreelancer(FreeLancer freelancer) {
        FreeLancer savedFreelancer = repository.save(freelancer);
        kafkaTemplate.send("freelancer-topic", "FREELANCER_CREATED: " + savedFreelancer.getId());
        return savedFreelancer;
    }

    public FreeLancer updateFreelancer(Long id, FreeLancer freeLancer) {
        // Update logic
        FreeLancer freeLancerEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id: " + id));

        freeLancerEntity.setFirstName(freeLancer.getFirstName());
        freeLancerEntity.setLastName(freeLancer.getLastName());
        freeLancerEntity.setDateOfBirth(freeLancer.getDateOfBirth());
        freeLancerEntity.setGender(freeLancer.getGender());
        kafkaTemplate.send("freelancer-topic", "FREELANCER_UPDATED: " + id);
        return repository.save(freeLancerEntity);
    }

    public void markForDeletion(Long id) {
        FreeLancer freelancer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id: " + id));
        freelancer.setStatus(FreeLancerStatus.MARKED_FOR_DELETION);
        freelancer.setDeletedDate(LocalDateTime.now());
        repository.save(freelancer);
        kafkaTemplate.send("freelancer-topic", "FREELANCER_DELETED: " + id);
    }

}
