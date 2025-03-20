package com.convertino.hire.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "job_positions")
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "name", length = 320, nullable = false)
    protected String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    protected String description;

    @OneToMany(mappedBy = "jobPosition", cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosition", cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Interview> interviews = new ArrayList<>();
}
