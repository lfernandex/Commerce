package com.fernandes.commerce.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderItemPK implements Serializable{

@ManyToOne
@JoinColumn(name = "order_id")
private Order order;

@ManyToOne
@JoinColumn(name = "product_id")
private Product product;

public OrderItemPK(){   
}

public OrderItemPK(Order order, Product product) {
    this.order = order;
    this.product = product;
}

public Order getOrder() {
    return order;
}

public void setOrder(Order order) {
    this.order = order;
}

public Product getProduct() {
    return product;
}

public void setProduct(Product product) {
    this.product = product;
}



}
