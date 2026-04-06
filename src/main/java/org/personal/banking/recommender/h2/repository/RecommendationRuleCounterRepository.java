package org.personal.banking.recommender.h2.repository;

import org.personal.banking.recommender.entities.RecommendationRuleCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationRuleCounterRepository
        extends JpaRepository<RecommendationRuleCounter, String> {

    Optional<RecommendationRuleCounter> findByRuleId(String ruleId);
}
