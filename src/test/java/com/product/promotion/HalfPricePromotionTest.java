package com.product.promotion;

import com.product.promotion.ProductPromotion.HalfPricePromotion;
import com.product.promotion.ProductPromotion.ProductMasterPrice;
import com.product.promotion.ProductPromotion.ShoppingCart;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;


public class HalfPricePromotionTest extends ShoppingCartDataProvider {

  @Test(dataProvider = DATA_PROVIDER_SHOPPING_CART)
  public void checkHalfPricePromotionApplicable(
      Map<String, ShoppingCart> shoppingCarts) {
    Set<String> promotionalProducts = new HashSet<>();
    promotionalProducts.add("A");
    promotionalProducts.add("B");

    boolean isPromotionApplicable = Boolean.TRUE;
    for (String productName : promotionalProducts) {
      if (!shoppingCarts.containsKey(productName)) {
        isPromotionApplicable = Boolean.FALSE;
        break;
      }
    }

    Assert.assertTrue(isPromotionApplicable);

    shoppingCarts.remove("A");
    isPromotionApplicable = Boolean.TRUE;
    for (String productName : promotionalProducts) {
      if (!shoppingCarts.containsKey(productName)) {
        isPromotionApplicable = Boolean.FALSE;
        break;
      }
    }
    Assert.assertFalse(isPromotionApplicable);
  }

  @Test(dataProvider = DATA_PROVIDER_HALF_PRICE_SHOPPING_PROMOTION)
  public void checkHalfPricePromotionApplicable(
      Set<String> promotionalProducts, Map<String, ProductMasterPrice> productMasterPriceMap,
      Map<String, ShoppingCart> shoppingCartMap) {

    HalfPricePromotion halfPricePromotion = new HalfPricePromotion(productMasterPriceMap,
        promotionalProducts);
    halfPricePromotion.calculatePromotion(shoppingCartMap);

    BigDecimal price = shoppingCartMap.entrySet().stream().map(Map.Entry::getValue)
        .map(shoppingCart -> shoppingCart.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);

    Assert.assertEquals(price, new BigDecimal("23.50"));
  }


}
