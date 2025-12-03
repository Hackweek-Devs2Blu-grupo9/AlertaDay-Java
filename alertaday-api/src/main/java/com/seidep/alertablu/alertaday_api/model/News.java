package com.seidep.alertablu.alertaday_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "news",
        indexes = {
            @Index(name = "idx_news_region", columnList = "region_id"),
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



}
