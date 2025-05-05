package com.xmind.entity;

import com.xmind.security.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demand_answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandAnswerEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "demand_id", nullable = false)
    private DemandEntity demand;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
