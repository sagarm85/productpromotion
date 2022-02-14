package com.product.promotion.ProductPromotion;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CheapProductFreePromotion implements Promotion {

  /**
   * information about Product name and price
   */
  Map<String, ProductMasterPrice> productMasterPrices;
  Set<String> promotionalProducts;
  int minimumProductsToPurchase = 3;

  public CheapProductFreePromotion(Map<String, ProductMasterPrice> productMasterPrices,
      Set<String> promotionalProducts) {
    this.productMasterPrices = productMasterPrices;
    this.promotionalProducts = promotionalProducts;
  }

  @Override
  public void calculatePromotion(Map<String, ShoppingCart> shoppingCartMap) {
    if (productMasterPrices.size() > 0) {
      int minimumThreeProducts = 0;
      ProductMasterPrice cheapProduct = null;
      BigDecimal minimumProductPrice = new BigDecimal(Double.MAX_VALUE);
      for (String productName : promotionalProducts) {
        if (shoppingCartMap.containsKey(productName)) {
          minimumThreeProducts++;

          if (shoppingCartMap.get(productName).getPrice().compareTo(minimumProductPrice) < 0) {
            minimumProductPrice = shoppingCartMap.get(productName).getPrice();
            cheapProduct = productMasterPrices.get(productName);
          }
        }
      }

      if (minimumThreeProducts >= minimumProductsToPurchase && Objects.nonNull(cheapProduct)) {
        if (shoppingCartMap.containsKey(cheapProduct.getProductName())) {
          shoppingCartMap.get(cheapProduct.productName).setPrice(Util.setScale(BigDecimal.ZERO));
        }
      }
    }
  }
}
