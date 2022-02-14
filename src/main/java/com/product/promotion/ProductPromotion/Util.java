package com.product.promotion.ProductPromotion;

import java.math.BigDecimal;

public class Util {

  public static BigDecimal setScale(BigDecimal number) {
    return number.setScale(2, BigDecimal.ROUND_HALF_UP);
  }
}
