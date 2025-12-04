package com.seidep.alertablu.alertaday_api.dto;

import com.seidep.alertablu.alertaday_api.model.enums.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record NewsDto(

        // ---------- CAMPOS DE SAÍDA (não obrigatórios na entrada) ----------
        UUID id,

        Instant createdAt,
        Instant updatedAt,

        Boolean isActive,

        String createdBy,

        // ---------- CAMPOS OBRIGATÓRIOS ----------
        @NotBlank @Size(max = 200)
        String title,

        @Size(max = 500)
        String summary,

        @NotBlank
        String body,

        @Size(max = 4000)
        String ttsText,

        String audioUrl,

        @NotNull
        Severity severity,

        String eventType,

        String region,

        Instant publishedAt,

        Instant expiresAt,

        Boolean isPublished

) {}
