package org.personal.banking.recommender.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rule_conditions")
public class RuleCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_id", nullable = false)
    private DynamicRule rule;

    @Column(name = "rule_type", nullable = false, length = 50)
    @JsonProperty("rule_type")
    private String ruleType;

    @Column(name = "query", nullable = false, length = 50)
    @JsonProperty("query")
    private String query;

    // Применение конвертера для преобразования List<String> ↔ TEXT[]
    @Convert(converter = org.personal.banking.recommender.converter.StringArrayConverter.class)
    @Column(name = "arguments", columnDefinition = "TEXT[]")
    @JsonProperty("arguments")
    private List<String> arguments = new ArrayList<>();

    @Column(name = "negate", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @JsonProperty("negate")
    private boolean negate = false;;

    public RuleCondition() {
    }

    public RuleCondition(String ruleType, String query, List<String> arguments, boolean negate) {
        this.ruleType = ruleType;
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public DynamicRule getRule() { return rule; }
    public void setRule(DynamicRule rule) { this.rule = rule; }

    public String getRuleType() { return ruleType; }
    public void setRuleType(String ruleType) { this.ruleType = ruleType; }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public List<String> getArguments() {
        if (arguments == null) {
            arguments = new ArrayList<>();
        }
        return arguments; }

    public void setArguments(List<String> arguments) {this.arguments = arguments != null ? new ArrayList<>(arguments) : new ArrayList<>();
    }

    public boolean isNegate() { return negate; }
    public void setNegate(boolean negate) { this.negate = negate; }
}