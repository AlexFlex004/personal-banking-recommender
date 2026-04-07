package org.personal.banking.recommender.dto;


import org.personal.banking.recommender.entities.DynamicRule;
import org.personal.banking.recommender.entities.RuleCondition;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record DynamicRuleDto(
        UUID id,
        UUID productId,
        String productName,
        String productText,
        List<RuleConditionDto> conditions
) {
    public static DynamicRuleDto fromEntity(DynamicRule rule) {
        return new DynamicRuleDto(
                rule.getId(),
                rule.getProductId(),
                rule.getProductName(),
                rule.getProductText(),
                rule.getConditions() != null
                        ? rule.getConditions().stream()
                        .map(RuleConditionDto::fromEntity)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }

    public DynamicRule toEntity() {
        DynamicRule entity = new DynamicRule();
        entity.setId(id);
        entity.setProductId(productId);
        entity.setProductName(productName);
        entity.setProductText(productText);

        if (conditions != null) {
            List<RuleCondition> conditionEntities = conditions.stream()
                    .map(dto -> dto.toEntity())
                    .collect(Collectors.toList());

            conditionEntities.forEach(condition -> condition.setRule(entity));

            entity.setConditions(conditionEntities);
        } else {
            entity.setConditions(java.util.Collections.emptyList());
        }

        return entity;
    }

}

