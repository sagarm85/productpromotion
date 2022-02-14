package com.product.promotion.ProductPromotion;

import java.math.BigDecimal;

public class ShoppingCart {
  String productName;
  BigDecimal price;
  Integer quantity;

  public ShoppingCart(String name, Integer quantity) {
    this.productName = name;
    this.quantity = quantity;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
