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

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService service;

    public DynamicRuleController(DynamicRuleService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DynamicRule> createRule(@RequestBody @Valid DynamicRule rule) {
        DynamicRule createdRule = service.createRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRule);
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

