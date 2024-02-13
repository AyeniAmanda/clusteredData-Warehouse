package com.example.clustereddatawarehouse.repository;

import com.example.clustereddatawarehouse.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealsRepository extends JpaRepository<Deal, Long> {
    Optional<Deal> findByDealUniqueId(String uniqueDealId);
}
