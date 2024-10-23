package com.tadaah.freelancer.dao;

import com.tadaah.freelancer.enums.FreeLancerStatus;
import com.tadaah.freelancer.model.FreeLancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FreeLancerRepository extends JpaRepository<FreeLancer, Long> {

    Optional<FreeLancer> findById(Long id);
    List<FreeLancer> findByStatus(FreeLancerStatus status);
    // Custom query to find freelancers deleted in the last 7 days
    @Query("SELECT f FROM FreeLancer f WHERE f.status = 'DELETED' AND f.deletedDate BETWEEN :startDate AND :endDate")
    List<FreeLancer> findAllDeletedFreelancersInLast7Days(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
