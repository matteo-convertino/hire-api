package com.convertino.hire.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "reports",
        uniqueConstraints = {
                @UniqueConstraint(name = "report_fk_ids_unique", columnNames = {"interview_id", "skill_id"})
        }
)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value", nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
