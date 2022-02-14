package com.product.promotion;


import static org.testng.Assert.assertEquals;

import com.product.promotion.ProductPromotion.Util;
import java.math.BigDecimal;
import org.testng.annotations.Test;

public class UtilTest {

  @Test
  public void setScale() {
    assertEquals(Util.setScale(BigDecimal.valueOf(10)), new BigDecimal("10.00"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.1)), new BigDecimal("10.10"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.11)), new BigDecimal("10.11"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.55)), new BigDecimal("10.55"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.50)), new BigDecimal("10.50"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.677)), new BigDecimal("10.68"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.075)), new BigDecimal("10.08"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.999)), new BigDecimal("11.00"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.956)), new BigDecimal("10.96"));
    assertEquals(Util.setScale(BigDecimal.valueOf(10.994)), new BigDecimal("10.99"));
  }
}
