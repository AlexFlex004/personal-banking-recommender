package org.personal.banking.recommender.controllers;

import jakarta.validation.Valid;
import org.personal.banking.recommender.dto.DynamicRuleDto;
import org.personal.banking.recommender.entities.DynamicRule;
import org.personal.banking.recommender.service.DynamicRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService service;
    private static final Logger logger = LoggerFactory.getLogger(DynamicRuleController.class);


    public DynamicRuleController(DynamicRuleService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DynamicRuleDto> createRule(@RequestBody @Valid DynamicRuleDto dto) {
        logger.info("Создание нового правила. Product ID: {}, Name: {}",
                dto.productId(), dto.productName());
        DynamicRuleDto saved = service.createRule(dto);
        logger.info("Правило успешно создано. ID: {}", saved.id());
        return ResponseEntity.ok(saved);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable UUID id) {
        service.deleteRule(id);
    }

    @GetMapping
    public ResponseEntity<List<DynamicRuleDto>> getAllRules() {
        List<DynamicRuleDto> rules = service.getAllRules();
        return ResponseEntity.ok(rules);
    }
}

