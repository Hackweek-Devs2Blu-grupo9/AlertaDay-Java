package com.seidep.alertablu.alertaday_api.dto;

import com.seidep.alertablu.alertaday_api.model.enums.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record AlertDto(

        // SAÍDA
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        Boolean isActive,
        String createdBy,

        // ENTRADA / SAÍDA
        @NotBlank
        @Size(max = 140)
        String title,

        @NotBlank
        @Size(max = 1000)
        String message,

        @NotNull
        Severity severity,

        @Size(max = 100)
        String eventType,

        String region,

        @Size(max = 100)
        String channels,   // Ex.: "APP,TTS,VIBRATION"

        UUID newsId,       // opcional: link para News

        Instant publishedAt,
        Instant expiresAt,

        Boolean isPublished
) {}
