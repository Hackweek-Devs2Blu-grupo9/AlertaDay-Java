package com.seidep.alertablu.alertaday_api.controller;


import com.seidep.alertablu.alertaday_api.dto.AlertDto;
import com.seidep.alertablu.alertaday_api.service.AlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlertDto create(@Valid @RequestBody AlertDto dto) {
        String createdBy = "system"; // depois você troca por usuário logado
        return alertService.create(dto, createdBy);
    }

    @GetMapping("/{id}")
    public AlertDto getById(@PathVariable UUID id) {
        return alertService.getById(id);
    }

    @GetMapping("/all")
    public List<AlertDto> listAll(
            @RequestParam(defaultValue = "false") boolean onlyActive
    ) {
        return alertService.listAll(onlyActive);
    }

    @GetMapping("/by-region")
    public List<AlertDto> listByRegion(
            @RequestParam String region,
            @RequestParam(defaultValue = "true") boolean onlyActive
    ) {
        return alertService.listByRegion(region, onlyActive);
    }

    @PutMapping("/{id}")
    public AlertDto update(@PathVariable UUID id,
                           @Valid @RequestBody AlertDto dto) {
        return alertService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        alertService.softDelete(id);
    }
}