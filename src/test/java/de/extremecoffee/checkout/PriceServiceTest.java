package de.extremecoffee.checkout;

import de.extremecoffee.checkout.services.PriceService;
import de.extremecoffee.checkout.entities.ShippingMethod;
import de.extremecoffee.checkout.constants.Constants;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
@QuarkusTest
public class PriceServiceTest {
  @Inject
  PriceService priceService;

  @Test
  public void testCalculateTaxWithPositiveSubTotal() {
    Double subTotal = 100.0;
    Double result = priceService.calculateTax(subTotal);
    Double expected = subTotal * Constants.TAX_RATE;
    Assertions.assertEquals(expected, result, 0.01);
  }

  @Test
  public void testCalculateTaxWithZeroSubTotal() {
    Double subTotal = 0.0;
    Double result = priceService.calculateTax(subTotal);
    Double expected = subTotal * Constants.TAX_RATE;
    Assertions.assertEquals(expected, result, 0.01);
  }

  @Test
  public void testCalculateTaxWithNegativeSubTotal() {
    Double subTotal = -100.0;
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      priceService.calculateTax(subTotal);
    });
  }

  @Test
  public void testGetShippingCostsBasePrice(){
    ShippingMethod shippingMethod = new ShippingMethod();
    shippingMethod.basePrice = 5;
    shippingMethod.reducedPrice = 3;
    var subTotal = 80d;
    var shippingCosts = priceService.getShippingCosts(subTotal, shippingMethod);
    Assertions.assertEquals(5, shippingCosts);
  }
  @Test
  public void testGetShippingCostsReducedPrice(){
    ShippingMethod shippingMethod = new ShippingMethod();
    shippingMethod.basePrice = 5;
    shippingMethod.reducedPrice = 3;
    var subTotal = 120d;
    var shippingCosts = priceService.getShippingCosts(subTotal, shippingMethod);
    Assertions.assertEquals(3, shippingCosts);
  }
}