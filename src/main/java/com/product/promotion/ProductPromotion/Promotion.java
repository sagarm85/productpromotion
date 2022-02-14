package com.product.promotion.ProductPromotion;

import java.util.Map;

public interface Promotion {

  void calculatePromotion(Map<String, ShoppingCart> shoppingCartMap);
}
