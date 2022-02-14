package com.product.promotion.ProductPromotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplyPromotions {

  List<Promotion> promotionList = new ArrayList<>();

  Map<String, ShoppingCart> shoppingCartMap = new HashMap<>();

  public ApplyPromotions(Map<String, ShoppingCart> shoppingCartMap) {
    this.shoppingCartMap = shoppingCartMap;
  }

  void addPromotions(Promotion promotion) {
    promotionList.add(promotion);
  }

  void applyPromotion() {
    for (Promotion promotion : promotionList) {
      promotion.calculatePromotion(shoppingCartMap);
    }
  }
}
