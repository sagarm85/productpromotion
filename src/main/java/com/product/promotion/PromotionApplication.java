package com.product.promotion;

import com.product.promotion.ProductPromotion.ShoppingCartService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PromotionApplication {

  public static void main(String[] args) {
    SpringApplication.run(PromotionApplication.class, args);
    ShoppingCartService.shoppingCartStart(args);

  }

}
