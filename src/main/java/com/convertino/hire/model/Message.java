package com.convertino.hire.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    protected String text;

    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;
}
