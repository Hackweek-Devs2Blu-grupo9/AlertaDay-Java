package com.seidep.alertablu.alertaday_api.repository;

import com.seidep.alertablu.alertaday_api.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {

    // Notícias de uma região
    List<News> findByRegionAndDeletedFalseOrderByPublishedAtDesc(String region);

    // Exemplo de consulta por período
    List<News> findByDeletedFalseAndPublishedAtBetweenOrderByPublishedAtDesc(Instant start, Instant end);

}
