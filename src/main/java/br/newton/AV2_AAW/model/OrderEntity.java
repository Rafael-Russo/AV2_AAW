package br.newton.AV2_AAW.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class OrderEntity {

    @Id
    private String id;
    private String productId;
    private String qnt;

    public OrderEntity() {
    }

    public OrderEntity(String productId, String qnt) {
        this.productId = productId;
        this.qnt = qnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }
}
