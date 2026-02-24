package org.personal.banking.recommender.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("id")
    private UUID id;

    @Column(name = "product_name")
    @JsonProperty("productName")
    private String productName;

    @Column(name = "product_id")
    @JsonProperty("productId")
    private UUID productId;

    @Column(name = "product_text")
    @JsonProperty("productText")
    private String productText;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonProperty("conditions")
    private List<RuleCondition> conditions = new ArrayList<>();


    public DynamicRule() {
    }

    public DynamicRule(String productName, UUID productId, String productText, List<RuleCondition> conditions) {
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.conditions = conditions != null ? conditions : new ArrayList<>();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RuleCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleCondition> conditions) {
        this.conditions = conditions != null ? conditions : new ArrayList<>();
    }

}
