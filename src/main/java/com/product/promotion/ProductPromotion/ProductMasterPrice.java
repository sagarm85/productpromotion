package com.product.promotion.ProductPromotion;


import java.io.Serializable;
import java.math.BigDecimal;

public class ProductMasterPrice implements Serializable {

  String productName;
  BigDecimal price;

  public ProductMasterPrice(String name, BigDecimal price) {
    this.productName = name;
    this.price = price;
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
}
