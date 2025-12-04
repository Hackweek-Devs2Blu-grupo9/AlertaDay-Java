package com.seidep.alertablu.alertaday_api.model;

import com.seidep.alertablu.alertaday_api.model.enums.Severity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "alerts",
        indexes = {
                @Index(name = "idx_alert_publishedAt", columnList = "published_at"),
                @Index(name = "idx_alert_severity", columnList = "severity")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(max = 140)
    private String title;   // Texto curto que aparece na notificação

    @NotBlank
    @Size(max = 1000)
    private String message; // Texto completo do alerta (curto, mas mais detalhado que o título)

    @Enumerated(EnumType.STRING)
    @NotNull
    private Severity severity;

    /**
     * Tipo de evento: enchente, deslizamento, vendaval, etc.
     */
    @Size(max = 100)
    private String eventType;

    private String region;

    /**
     * Canais de envio, ex: "APP,EMAIL,SMS" ou "APP,TTS,VIBRATION".
     * Mantido como String simples por enquanto pra não complicar.
     */
    @Size(max = 100)
    private String channels;

    /**
     * ID de uma notícia associada (detalhamento do alerta).
     * Opcional – você pode ligar alerta a uma News específica.
     */
    @Column(name = "news_id")
    private UUID newsId;

    @Column(name = "published_at")
    private Instant publishedAt;  // Quando o alerta foi/será disparado

    @Column(name = "expires_at")
    private Instant expiresAt;    // Depois disso, o alerta deixa de ser relevante

    private boolean isPublished;  // Se já foi publicado/enviado ou não

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private String createdBy;

    private boolean deleted = false;

    @Version
    private Long version;

    /**
     * Conveniência para saber se o alerta está ativo agora.
     */
    public boolean isActive() {
        Instant now = Instant.now();
        if (deleted) return false;
        if (!isPublished) return false;
        if (publishedAt != null && now.isBefore(publishedAt)) return false;
        if (expiresAt != null && now.isAfter(expiresAt)) return false;
        return true;
    }
}
