package com.ddd.oi.faq.domain;

import com.ddd.oi.common.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faq")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Faq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
