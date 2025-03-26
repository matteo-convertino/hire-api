package com.convertino.hire.model;

import jakarta.persistence.*;
import lombok.Data;
import swiss.ameri.gemini.api.Content;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Content.Role role;

    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;
}
