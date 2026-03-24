package org.personal.banking.recommender.rules;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_rule_counter")
public class RecommendationRuleCounter {

    // 1. Изменяем тип ID на String, так как rule_id является бизнес-ключом.
    //    Используем UUID-генератор Hibernate для генерации случайных строк.
    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private String id;

    /**
     * Идентификатор правила (соответствует id в RecommendationDto).
     * Сделан уникальным, чтобы для каждого правила был только один счётчик.
     */
    @Column(name = "rule_id", nullable = false, unique = true)
    private String ruleId;

    @Column(nullable = false)
    private Long count = 0L;

    @Column(name = "last_triggered_at")
    private LocalDateTime lastTriggeredAt;

    protected RecommendationRuleCounter() {}

    /**
     * Создаёт новый счётчик для правила.
     * @param ruleId идентификатор правила (например, из RecommendationDto.getId())
     */
    public RecommendationRuleCounter(String ruleId) {
        this.ruleId = ruleId;
        this.lastTriggeredAt = LocalDateTime.now();
        // ID будет сгенерировано автоматически при сохранении в БД
    }

    /**
     * Увеличивает счётчик и обновляет время последнего срабатывания.
     */
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
