package com.seidep.alertablu.alertaday_api.controller;

import com.seidep.alertablu.alertaday_api.dto.AlertDto;
import com.seidep.alertablu.alertaday_api.dto.NewsDto;
import com.seidep.alertablu.alertaday_api.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto create(@Valid @RequestBody NewsDto dto) {
        String createdBy = "system"; // Mudar quando tiver autenticação
        return newsService.create(dto, createdBy);
    }

    @GetMapping("/{id}")
    public NewsDto getById(@PathVariable UUID id) {
        return newsService.getById(id);
    }

    @GetMapping("/all")
    public List<NewsDto> listAll(
            @RequestParam(defaultValue = "false") boolean onlyActive
    ) {
        return newsService.listAll(onlyActive);
    }

    @GetMapping
    public List<NewsDto> listByRegion(
            @RequestParam String region,
            @RequestParam(defaultValue = "true") boolean onlyActive
    ) {
        return newsService.listByRegion(region, onlyActive);
    }

    @PutMapping("/{id}")
    public NewsDto update(@PathVariable UUID id, @Valid @RequestBody NewsDto dto) {
        return newsService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        newsService.softDelete(id);
    }
}