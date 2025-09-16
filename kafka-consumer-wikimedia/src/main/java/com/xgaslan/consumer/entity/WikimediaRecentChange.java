package com.xgaslan.consumer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wikimedia_recent_change")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WikimediaRecentChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String wikiEvent;
}
