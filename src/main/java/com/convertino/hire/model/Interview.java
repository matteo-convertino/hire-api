package com.convertino.hire.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "interview", cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "interview", cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Report> reports = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_position_id", nullable = false)
    private JobPosition jobPosition;
}
