package com.product.promotion.ProductPromotion;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HalfPricePromotion implements Promotion {

  /**
   * information about Product name and price
   */
  Map<String, ProductMasterPrice> productMasterPrices;

  Set<String> promotionalProducts;
  Double halfPercentPromotion = 0.5d;

  public HalfPricePromotion(Map<String, ProductMasterPrice> productMasterPrices,
      Set<String> promotionalProducts) {
    this.productMasterPrices = productMasterPrices;
    this.promotionalProducts = promotionalProducts;
  }

  @Override
  /**
   * shoppingCard -> Having product name and quantities information
   */
  public void calculatePromotion(Map<String, ShoppingCart> shoppingCartMap) {
    boolean isPromotionApplicable = Boolean.TRUE;
    Map<String, ProductMasterPrice> promotionalProductMasters = new HashMap<>();
    for (String productName : promotionalProducts) {
      if (!shoppingCartMap.containsKey(productName)) {
        isPromotionApplicable = Boolean.FALSE;
        break;
      } else {
        promotionalProductMasters.put(productName, productMasterPrices.get(productName));
      }
    }

    if (isPromotionApplicable) {
      List<ProductMasterPrice> promotionalProducts = promotionalProductMasters.values().stream()
          .sorted(Comparator.comparingDouble(product -> product.getPrice().doubleValue())).collect(
              Collectors.toList());
      ProductMasterPrice cheapProduct = promotionalProducts.get(0);
      ProductMasterPrice expensiveProduct = promotionalProducts.get(1);

      ShoppingCart expensiveShoppingCart = shoppingCartMap.get(expensiveProduct.productName);
      ShoppingCart cheapShoppingCart = shoppingCartMap.get(cheapProduct.productName);

      Integer qtyToConsiderForPromotion =
          expensiveShoppingCart.getQuantity() > cheapShoppingCart.getQuantity() ?
              expensiveShoppingCart.getQuantity() - cheapShoppingCart.getQuantity()
              : expensiveShoppingCart.getQuantity();
      Integer remainingCheapProduct = Math.abs(
          expensiveShoppingCart.getQuantity() - cheapShoppingCart.getQuantity());

      expensiveShoppingCart.setPrice(Util.setScale(
          expensiveProduct.getPrice()
              .multiply(BigDecimal.valueOf(expensiveShoppingCart.getQuantity()))));
      cheapShoppingCart.setPrice(Util.setScale(
          (cheapProduct.getPrice()
              .multiply(BigDecimal.valueOf(qtyToConsiderForPromotion * halfPercentPromotion)))
              .add(cheapProduct.getPrice()
                  .multiply(BigDecimal.valueOf(remainingCheapProduct)))));
    }
  }
}
