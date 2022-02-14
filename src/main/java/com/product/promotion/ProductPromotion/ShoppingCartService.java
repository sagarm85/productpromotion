package com.product.promotion.ProductPromotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ShoppingCartService {

  public static void shoppingCartStart(String[] args) {

    Map<String, ProductMasterPrice> productMasterPriceMap = setProductMasterMap();

    List<ShoppingCart> shoppingCarts = fillShoppingCart(productMasterPriceMap);

    Map<String, ShoppingCart> shoppingCartMap = shoppingCarts.stream().collect(
        Collectors.toMap(shoppingCart -> shoppingCart.productName, shoppingCart -> shoppingCart));

    ApplyPromotions applyPromotions = new ApplyPromotions(shoppingCartMap);
    applyPromotions.addPromotions(getHalfPricePromotion(productMasterPriceMap));
    applyPromotions.addPromotions(getCheapProductFreePromotion(productMasterPriceMap));
    applyPromotions.applyPromotion();

    BigDecimal price = shoppingCartMap.entrySet().stream().map(Map.Entry::getValue)
        .map(shoppingCart -> shoppingCart.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    System.out.println("Shopping cart price is : " + price.setScale(2, BigDecimal.ROUND_HALF_UP));

  }

  private static CheapProductFreePromotion getCheapProductFreePromotion(
      Map<String, ProductMasterPrice> productMasterPriceMap) {
    Set<String> promotionalProducts;
    promotionalProducts = new HashSet<>();
    promotionalProducts.add("X");
    promotionalProducts.add("Y");
    promotionalProducts.add("Z");
    promotionalProducts.add("P");
    promotionalProducts.add("Q");
    CheapProductFreePromotion cheapProductFreePromotion = new CheapProductFreePromotion(
        productMasterPriceMap, promotionalProducts);
    return cheapProductFreePromotion;
  }

  private static HalfPricePromotion getHalfPricePromotion(
      Map<String, ProductMasterPrice> productMasterPriceMap) {
    Set<String> promotionalProducts = new HashSet<>();
    promotionalProducts.add("A");
    promotionalProducts.add("B");
    HalfPricePromotion halfPricePromotion = new HalfPricePromotion(productMasterPriceMap,
        promotionalProducts);
    return halfPricePromotion;
  }

  private static List<ShoppingCart> fillShoppingCart(Map<String, ProductMasterPrice> productMasterPriceMap) {
    List<ShoppingCart> shoppingCarts = new ArrayList<>();
    shoppingCarts.add(new ShoppingCart("X", 1));
    shoppingCarts.add(new ShoppingCart("Y", 1));
    shoppingCarts.add(new ShoppingCart("Z", 1));
    shoppingCarts.add(new ShoppingCart("R", 1));
    shoppingCarts.add(new ShoppingCart("A", 1));
    shoppingCarts.add(new ShoppingCart("B", 2));
    calculatePriceWithoutPromotion(shoppingCarts, productMasterPriceMap);
    return shoppingCarts;
  }

  private static Map<String, ProductMasterPrice> setProductMasterMap() {
    Map<String, ProductMasterPrice> productMasterPriceMap = new HashMap<>();
    addMasterProduct(productMasterPriceMap, "A", new BigDecimal(10));
    addMasterProduct(productMasterPriceMap, "B", new BigDecimal(9));
    addMasterProduct(productMasterPriceMap, "X", new BigDecimal(10));
    addMasterProduct(productMasterPriceMap, "Y", new BigDecimal(5));
    addMasterProduct(productMasterPriceMap, "Z", new BigDecimal(4));
    addMasterProduct(productMasterPriceMap, "P", new BigDecimal(3));
    addMasterProduct(productMasterPriceMap, "Q", new BigDecimal(8));
    addMasterProduct(productMasterPriceMap, "R", new BigDecimal(2));
    return productMasterPriceMap;
  }

  private static void calculatePriceWithoutPromotion(List<ShoppingCart> shoppingCarts,
      Map<String, ProductMasterPrice> productMasterPriceMap) {
    for (ShoppingCart cart : shoppingCarts) {
      if (productMasterPriceMap.containsKey(cart.getProductName())) {
        cart.setPrice(Util.setScale(productMasterPriceMap.get(cart.getProductName()).getPrice()
            .multiply(BigDecimal.valueOf(cart.getQuantity()))));
      } else {
        System.out.println("Product inventory is not available in the master list");
        cart.setPrice(BigDecimal.ZERO);
        cart.setQuantity(0);
      }
    }
  }

  private static void addMasterProduct(Map<String, ProductMasterPrice> productMasterPriceMap,
      String productName,
      BigDecimal price) {
    productMasterPriceMap.put(productName, new ProductMasterPrice(productName, price));
  }
}
