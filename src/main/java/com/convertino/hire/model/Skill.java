package com.convertino.hire.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "job_position_id", nullable = false)
    private JobPosition jobPosition;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Report> reports = new ArrayList<>();
}
