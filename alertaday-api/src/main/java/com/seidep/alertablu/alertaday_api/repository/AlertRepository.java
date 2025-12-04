package com.seidep.alertablu.alertaday_api.repository;

import com.seidep.alertablu.alertaday_api.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, UUID> {

    List<Alert> findByRegionAndDeletedFalseOrderByPublishedAtDesc(String region);

    List<Alert> findByDeletedFalseAndPublishedAtBetweenOrderByPublishedAtDesc(
            Instant start, Instant end
    );
}