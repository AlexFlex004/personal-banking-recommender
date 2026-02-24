package org.personal.banking.recommender.dto;

import org.personal.banking.recommender.entities.DynamicRule;
import java.util.UUID;

public record DynamicRuleDto(
        UUID id,
        UUID productId,
        String productName,
        String productText
) {
    public static DynamicRuleDto fromEntity(DynamicRule rule) {
        return new DynamicRuleDto(
                rule.getId(),
                rule.getProductId(),
                rule.getProductName(),
                rule.getProductText()
        );
    }
}
