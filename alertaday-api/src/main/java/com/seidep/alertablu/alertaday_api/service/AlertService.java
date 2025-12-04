package com.seidep.alertablu.alertaday_api.service;

import com.seidep.alertablu.alertaday_api.dto.AlertDto;
import com.seidep.alertablu.alertaday_api.model.Alert;
import com.seidep.alertablu.alertaday_api.repository.AlertRepository;
import com.seidep.alertablu.alertaday_api.service.mapper.AlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper mapper;

    @Transactional
    public AlertDto create(AlertDto dto, String createdBy) {
        Alert entity = mapper.toEntity(dto);
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        Alert saved = alertRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public AlertDto getById(UUID id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + id));
        return mapper.toDto(alert);
    }

    @Transactional(readOnly = true)
    public List<AlertDto> listByRegion(String region, boolean onlyActive) {
        List<Alert> list = alertRepository.findByRegionAndDeletedFalseOrderByPublishedAtDesc(region);

        return list.stream()
                .filter(a -> !onlyActive || a.isActive())
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlertDto> listAll(boolean onlyActive) {
        return alertRepository.findAll().stream()
                .filter(a -> !a.isDeleted())
                .filter(a -> !onlyActive || a.isActive())
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public AlertDto update(UUID id, AlertDto dto) {
        Alert existing = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + id));

        Alert updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setCreatedBy(existing.getCreatedBy());
        updated.setDeleted(existing.isDeleted());

        Alert saved = alertRepository.save(updated);
        return mapper.toDto(saved);
    }

    @Transactional
    public void softDelete(UUID id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + id));
        alert.setDeleted(true);
        alertRepository.save(alert);
    }
}