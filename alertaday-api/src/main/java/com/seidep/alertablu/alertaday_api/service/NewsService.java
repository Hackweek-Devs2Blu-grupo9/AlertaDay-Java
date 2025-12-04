package com.seidep.alertablu.alertaday_api.service;

import com.seidep.alertablu.alertaday_api.dto.NewsDto;
import com.seidep.alertablu.alertaday_api.model.News;
import com.seidep.alertablu.alertaday_api.repository.NewsRepository;
import com.seidep.alertablu.alertaday_api.service.mapper.NewsMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper mapper;

    @Transactional
    public NewsDto create(NewsDto dto, String createdBy) {

        News entity = mapper.toEntity(dto);
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        News saved = newsRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    public NewsDto getById(UUID id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found: " + id));
        return mapper.toDto(news);
    }

    @Transactional
    public List<NewsDto> listByRegion(String region, boolean activeOnly) {
        List<News> list = newsRepository.findByRegionAndDeletedFalseOrderByPublishedAtDesc(region);

        return list.stream()
                .filter(n -> !activeOnly || n.isActive())
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public NewsDto update(UUID id, NewsDto dto) {
        News existing = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found: " + id));

        News updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setCreatedBy(existing.getCreatedBy());

        News saved = newsRepository.save(updated);
        return mapper.toDto(saved);
    }

    @Transactional
    public void softDelete(UUID id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found: " + id));
        news.setDeleted(true);
        newsRepository.save(news);
    }

    @Transactional
    public List<NewsDto> listAll(boolean onlyActive) {
        List<News> list = newsRepository.findAll()
                .stream()
                .filter(n -> !n.isDeleted()) // ignora deletados
                .filter(n -> !onlyActive || n.isActive()) // filtra ativos se solicitado
                .toList();

        return list.stream()
                .map(mapper::toDto)
                .toList();
    }
}
