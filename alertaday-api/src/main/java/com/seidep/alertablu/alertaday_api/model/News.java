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
@Table(name = "news",
        indexes = {
            @Index(name = "idx_news_publishedAt", columnList = "published_at"),
            @Index(name = "idx_news_severity", columnList = "severity")
        }

      )
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class News {
    @Id
    @GeneratedValue()
    private UUID id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 500)
    private String summary;

    @Lob
    @NotBlank
    private String body;

    @Size(max = 4000)
    private String ttsText;

    private String audioUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Severity severity;

    private String eventType;

    private String region;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    private boolean isPublished;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private String createdBy;

    private boolean deleted = false;

    @Version
    private Long version;

    public boolean isActive() {
        Instant now = Instant.now();
        if (deleted) return false;
        if (!isPublished) return false;
        if (publishedAt != null && now.isBefore(publishedAt)) return false;
        if (expiresAt != null && now.isAfter(expiresAt)) return false;
        return true;
    }

}
