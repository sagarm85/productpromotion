package com.product.promotion;

import com.product.promotion.ProductPromotion.ProductMasterPrice;
import com.product.promotion.ProductPromotion.ShoppingCart;
import com.product.promotion.ProductPromotion.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;

public class ShoppingCartDataProvider {

  protected static final String DATA_PROVIDER_HALF_PRICE_SHOPPING_PROMOTION = "dataProviderHalfPriceShoppingPromotion";
  protected static final String DATA_PROVIDER_SHOPPING_CART = "dataProviderShoppingCart";
  protected static final String DATA_PROVIDER_PRODUCT_MASTER = "dataProviderProductMaster";

  @DataProvider
  public Object[][] dataProviderHalfPriceShoppingPromotion() {
    Map<String, ProductMasterPrice> productMasterPriceMap = setProductMasterHalfPriceMap();
    Object[][] objects = {
        {halfPromotionProducts(), productMasterPriceMap,
            fillShoppingCartForHalfPrice(productMasterPriceMap)}
    };
    return objects;
  }

  @DataProvider
  public Object[][] dataProviderShoppingCart() {
    Map<String, ProductMasterPrice> productMasterPriceMap = setProductMasterMap();
    Object[][] objects = {
        {fillShoppingCart(productMasterPriceMap)}
    };
    return objects;
  }

  @DataProvider
  public Object[][] dataProviderProductMaster() {

    Map<String, ProductMasterPrice> productMasterPriceMap = setProductMasterMap();
    Object[][] objects = {
        {productMasterPriceMap}
    };
    return objects;
  }

  public Set<String> halfPromotionProducts() {
    Set<String> promotionalProducts = new HashSet<>();
    promotionalProducts.add("A");
    promotionalProducts.add("B");
    return promotionalProducts;
  }

  public Map<String, ShoppingCart> fillShoppingCart(
      Map<String, ProductMasterPrice> productMasterPriceMap) {
    List<ShoppingCart> shoppingCarts = new ArrayList<>();
    shoppingCarts.add(new ShoppingCart("X", 1));
    shoppingCarts.add(new ShoppingCart("Y", 1));
    shoppingCarts.add(new ShoppingCart("Z", 1));
    shoppingCarts.add(new ShoppingCart("R", 1));
    shoppingCarts.add(new ShoppingCart("A", 1));
    shoppingCarts.add(new ShoppingCart("B", 2));

    Map<String, ShoppingCart> shoppingCartMap = shoppingCarts.stream().collect(
        Collectors.toMap(shoppingCart -> shoppingCart.getProductName(),
            shoppingCart -> shoppingCart));

    calculatePriceWithoutPromotion(shoppingCarts, productMasterPriceMap);
    return shoppingCartMap;
  }

  public Map<String, ShoppingCart> fillShoppingCartForHalfPrice(
      Map<String, ProductMasterPrice> productMasterPriceMap) {
    List<ShoppingCart> shoppingCarts = new ArrayList<>();
    shoppingCarts.add(new ShoppingCart("A", 1));
    shoppingCarts.add(new ShoppingCart("B", 2));

    Map<String, ShoppingCart> shoppingCartMap = shoppingCarts.stream().collect(
        Collectors.toMap(shoppingCart -> shoppingCart.getProductName(),
            shoppingCart -> shoppingCart));

    calculatePriceWithoutPromotion(shoppingCarts, productMasterPriceMap);
    return shoppingCartMap;
  }

  private void calculatePriceWithoutPromotion(List<ShoppingCart> shoppingCarts,
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

  private Map<String, ProductMasterPrice> setProductMasterMap() {
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

  private Map<String, ProductMasterPrice> setProductMasterHalfPriceMap() {
    Map<String, ProductMasterPrice> productMasterPriceMap = new HashMap<>();
    addMasterProduct(productMasterPriceMap, "A", new BigDecimal(10));
    addMasterProduct(productMasterPriceMap, "B", new BigDecimal(9));
    return productMasterPriceMap;
  }

  public void addMasterProduct(Map<String, ProductMasterPrice> productMasterPriceMap,
      String productName,
      BigDecimal price) {
    productMasterPriceMap.put(productName, new ProductMasterPrice(productName, price));
  }
}
