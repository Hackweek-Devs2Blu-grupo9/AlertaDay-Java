package com.seidep.alertablu.alertaday_api.service.mapper;

import com.seidep.alertablu.alertaday_api.dto.AlertDto;
import com.seidep.alertablu.alertaday_api.model.Alert;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AlertMapper {

    public AlertDto toDto(Alert entity) {
        return new AlertDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isActive(),
                entity.getCreatedBy(),

                entity.getTitle(),
                entity.getMessage(),
                entity.getSeverity(),
                entity.getEventType(),
                entity.getRegion(),
                entity.getChannels(),
                entity.getNewsId(),
                entity.getPublishedAt(),
                entity.getExpiresAt(),
                entity.isPublished()
        );
    }

    public Alert toEntity(AlertDto dto) {
        Instant now = Instant.now();

        boolean published = Boolean.TRUE.equals(dto.isPublished());
        Instant publishedAt = dto.publishedAt() != null
                ? dto.publishedAt()
                : (published ? now : null);

        Alert alert = new Alert();
        alert.setId(dto.id());
        alert.setTitle(dto.title());
        alert.setMessage(dto.message());
        alert.setSeverity(dto.severity());
        alert.setEventType(dto.eventType());
        alert.setRegion(dto.region());
        alert.setChannels(dto.channels());
        alert.setNewsId(dto.newsId());
        alert.setPublishedAt(publishedAt);
        alert.setExpiresAt(dto.expiresAt());
        alert.setPublished(published);

        // createdBy e deleted são preenchidos no service
        return alert;
    }
}
