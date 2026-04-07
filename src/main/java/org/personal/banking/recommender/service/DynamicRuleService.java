package org.personal.banking.recommender.service;

import org.personal.banking.recommender.dto.DynamicRuleDto;
import org.personal.banking.recommender.entities.DynamicRule;
import org.personal.banking.recommender.entities.RuleCondition;
import org.personal.banking.recommender.postgres.repository.DynamicRuleRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DynamicRuleService {

    private final DynamicRuleRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(DynamicRuleService.class);


    public DynamicRuleService(DynamicRuleRepository repository) {
        this.repository = repository;
    }
   // 1. Добавление нового правила
    @Transactional
    public DynamicRuleDto createRule(DynamicRuleDto dto) {
        DynamicRule entity = dto.toEntity();

        logger.debug("Сохраняем правило с ID: {}. Количество условий: {}",
                entity.getId(), entity.getConditions().size());
        for (RuleCondition condition : entity.getConditions()) {
            logger.debug("Условие ID: {}, Rule: {}", condition.getId(), condition.getRule());
        }

        DynamicRule saved = repository.save(entity);
        return DynamicRuleDto.fromEntity(saved);
    }


    // 2. Удаление правила
    public void deleteRule(UUID ruleId) {
        try {
            logger.info("Начинаем удаление правила. ID: {}", ruleId);
            if (!repository.existsById(ruleId)) {
                logger.warn("Правило с ID {} не найдено в БД. Пропускаем удаление.", ruleId);
                return;
            }
            repository.deleteById(ruleId);
            logger.info("Правило с ID {} успешно удалено.", ruleId);
        } catch (Exception e) {
            logger.error("Ошибка при удалении правила с ID {}. Сообщение: {}",
                    ruleId, e.getMessage(), e);
            throw e;
        }
    }

    // 3. Получение всех правил
    @Transactional(readOnly = true)
    public List<DynamicRuleDto> getAllRules() {
        Logger logger = LoggerFactory.getLogger(DynamicRuleService.class);

        logger.debug("Начало загрузки всех динамических правил");

        List<DynamicRule> rules = repository.findAllWithConditions();
        logger.info("Загружено {} правил", rules.size());

        for (DynamicRule rule : rules) {
            logger.debug("Правило ID: {}, Conditions: {}",
                    rule.getId(),
                    rule.getConditions() != null ? rule.getConditions().size() : 0);
        }

        List<DynamicRuleDto> dtos = rules.stream()
                .map(DynamicRuleDto::fromEntity)
                .collect(Collectors.toList());

        for (DynamicRuleDto dto : dtos) {
            logger.debug("DTO ID: {}, ConditionsCount: {}",
                    dto.id(), dto.conditions().size());
        }

        return dtos;
    }


}