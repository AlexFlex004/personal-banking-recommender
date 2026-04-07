package org.personal.banking.recommender.dto;

import org.personal.banking.recommender.entities.RuleCondition;

import java.util.List;
import java.util.UUID;

public record RuleConditionDto(
        UUID id,
        String ruleType,
        String query,
        List<String> arguments,
        boolean negate
) {
    public static RuleConditionDto fromEntity(RuleCondition condition) {
        return new RuleConditionDto(
                condition.getId(),
                condition.getRuleType(),
                condition.getQuery(),
                condition.getArguments(),
                condition.isNegate()
        );
    }

    public RuleCondition toEntity() {
        RuleCondition entity = new RuleCondition();
        entity.setId(id);
        entity.setRuleType(ruleType);
        entity.setQuery(query);
        entity.setArguments(arguments);
        entity.setNegate(negate);
        return entity;
    }
}

