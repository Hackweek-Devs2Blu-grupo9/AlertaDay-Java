package com.seidep.alertablu.alertaday_api.service.mapper;

import com.seidep.alertablu.alertaday_api.dto.NewsDto;
import com.seidep.alertablu.alertaday_api.model.News;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NewsMapper {

    public NewsDto toDto(News entity) {
        return new NewsDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isActive(),
                entity.getCreatedBy(),

                entity.getTitle(),
                entity.getSummary(),
                entity.getBody(),
                entity.getTtsText(),
                entity.getAudioUrl(),
                entity.getSeverity(),
                entity.getEventType(),
                entity.getRegion(),
                entity.getPublishedAt(),
                entity.getExpiresAt(),
                entity.isPublished()
        );
    }

    public News toEntity(NewsDto dto) {
        Instant now = Instant.now();

        boolean published = Boolean.TRUE.equals(dto.isPublished());
        Instant publishedAt = dto.publishedAt() != null
                ? dto.publishedAt()
                : (published ? now : null);

        News news = new News();
        news.setId(dto.id());
        news.setTitle(dto.title());
        news.setSummary(dto.summary());
        news.setBody(dto.body());
        news.setTtsText(dto.ttsText());
        news.setAudioUrl(dto.audioUrl());
        news.setSeverity(dto.severity());
        news.setEventType(dto.eventType());
        news.setRegion(dto.region());
        news.setPublishedAt(publishedAt);
        news.setExpiresAt(dto.expiresAt());
        news.setPublished(published);
        news.setCreatedBy(dto.createdBy());

        return news;
    }
}
