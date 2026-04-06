package org.personal.banking.recommender.controllers;

import org.personal.banking.recommender.h2.repository.RecommendationRuleCounterRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuleStatsController {

    private final RecommendationRuleCounterRepository repository;

    public RuleStatsController(RecommendationRuleCounterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<Map<String, Object>> stats = repository.findAll().stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("rule_id", r.getRuleId());
                    map.put("count", r.getCount());
                    return map;
                })
                .collect(Collectors.toList());
        return Map.of("stats", stats);
    }
}


