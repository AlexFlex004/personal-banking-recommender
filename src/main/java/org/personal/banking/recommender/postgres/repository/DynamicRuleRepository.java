package org.personal.banking.recommender.postgres.repository;

import jakarta.persistence.Table;
import org.personal.banking.recommender.entities.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, UUID> {

    @Query("SELECT dr FROM DynamicRule dr LEFT JOIN FETCH dr.conditions")
    List<DynamicRule> findAllWithConditions();

}

