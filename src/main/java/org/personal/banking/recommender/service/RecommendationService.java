package org.personal.banking.recommender.service;

import org.personal.banking.recommender.dto.RecommendationDto;
import org.personal.banking.recommender.entities.DynamicRule;
import org.personal.banking.recommender.entities.RuleCondition;
import org.personal.banking.recommender.postgres.repository.DynamicRuleRepository;
import org.personal.banking.recommender.h2.repository.RecommendationRepository;
import org.personal.banking.recommender.rules.RecommendationRules;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRules> rules;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final RecommendationRepository repository;

    public RecommendationService(
            List<RecommendationRules> rules,
            DynamicRuleRepository dynamicRuleRepository,
            RecommendationRepository repository
    ) {
        this.rules = rules;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.repository = repository;
    }


    public List<RecommendationDto> getRecommendations(UUID userId) {
        List<RecommendationDto> result = new ArrayList<>();

        for (RecommendationRules rule : rules) {
            try {
                Optional<RecommendationDto> recommendation = rule.check(userId);
                recommendation.ifPresent(result::add);
            } catch (Exception e) {
                System.err.println("Ошибка в правиле " + rule.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        return result;
    }


    private boolean evaluateDynamicRule(UUID userId, DynamicRule rule) {
        for (RuleCondition condition : rule.getConditions()) {
            boolean conditionResult = evaluateCondition(userId, condition);
            if (!conditionResult) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateCondition(UUID userId, RuleCondition condition) {
        switch (condition.getQuery()) {
            case "USER_OF":
                return checkUserOf(userId, condition.getArguments().get(0), condition.isNegate());
            case "ACTIVE_USER_OF":
                return checkActiveUserOf(userId, condition.getArguments().get(0), condition.isNegate());
            case "TRANSACTION_SUM_COMPARE":
                return checkTransactionSumCompare(userId, condition, condition.isNegate());
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                return checkDepositWithdrawCompare(userId, condition, condition.isNegate());
            default:
                throw new IllegalArgumentException("Неизвестный тип запроса: " + condition.getQuery());
        }
    }

    // Реализация проверок для каждого типа запроса
    private boolean checkUserOf(UUID userId, String productType, boolean negate) {
        boolean hasProduct = repository.hasProductType(userId, productType);
        return negate ? !hasProduct : hasProduct;
    }

    private boolean checkActiveUserOf(UUID userId, String productType, boolean negate) {
        int transactionCount = repository.countTransactionsByProduct(userId, productType);
        boolean isActive = transactionCount >= 5;
        return negate ? !isActive : isActive;
    }

    private boolean checkTransactionSumCompare(UUID userId, RuleCondition condition, boolean negate) {
        String productType = condition.getArguments().get(0);
        String transactionType = condition.getArguments().get(1);
        String operator = condition.getArguments().get(2);
        BigDecimal threshold = new BigDecimal(condition.getArguments().get(3));

        BigDecimal sum = repository.sumByProductAndTransaction(userId, productType, transactionType);

        boolean result = compare(sum, threshold, operator);
        return negate ? !result : result;
    }

    private boolean checkDepositWithdrawCompare(UUID userId, RuleCondition condition, boolean negate) {
        String productType = condition.getArguments().get(0);
        String operator = condition.getArguments().get(1);

        BigDecimal depositSum = repository.sumByProductAndTransaction(userId, productType, "DEPOSIT");
        BigDecimal withdrawSum = repository.sumByProductAndTransaction(userId, productType, "WITHDRAWAL");

        boolean result = compare(depositSum, withdrawSum, operator);
        return negate ? !result : result;
    }

    private boolean compare(BigDecimal a, BigDecimal b, String operator) {
        switch (operator) {
            case ">":
                return a.compareTo(b) > 0;
            case "<":
                return a.compareTo(b) < 0;
            case "=":
                return a.equals(b);
            case ">=":
                return a.compareTo(b) >= 0;
            case "<=":
                return a.compareTo(b) <= 0;
            default:
                throw new IllegalArgumentException("Неверный оператор сравнения: " + operator);
        }
    }

    public List<RecommendationDto> getRecommendationsByUsername(String username) {

        List<UUID> users = repository.findUserIdsByUsername(username);

        if (users.size() != 1) {
            throw new RuntimeException("Пользователь не найден");
        }

        UUID userId = users.get(0);

        return getRecommendations(userId);
    }

    public String getFullNameByUsername(String username) {

        List<String> names = repository.findFullNamesByUsername(username);

        if (names.size() != 1) {
            throw new RuntimeException("Пользователь не найден");
        }

        return names.get(0);
    }
}
