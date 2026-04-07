package org.personal.banking.recommender.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_rule_counter")
public class RecommendationRuleCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "rule_id", nullable = false, unique = true)
    private String ruleId;

    @Column(nullable = false)
    private Long count = 0L;

    @Column(name = "last_triggered_at")
    private LocalDateTime lastTriggeredAt;

    protected RecommendationRuleCounter() {}

    public RecommendationRuleCounter(String ruleId) {
        this.ruleId = ruleId;
        this.lastTriggeredAt = LocalDateTime.now();
    }

    public void increment() {
        this.count++;
        this.lastTriggeredAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public Long getCount() {
        return count;
    }

    public LocalDateTime getLastTriggeredAt() {
        return lastTriggeredAt;
    }
}
