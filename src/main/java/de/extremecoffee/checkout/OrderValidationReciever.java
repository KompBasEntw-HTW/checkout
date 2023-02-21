package de.extremecoffee.checkout;

import de.extremecoffee.checkout.dtos.OrderValidationResponseDto;
import de.extremecoffee.checkout.services.CheckoutService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderValidationReciever {
  @Inject
  CheckoutService service;

  @Incoming("order-validation")
  @Blocking
  public void recieveOrderValidation(JsonObject p) {
    OrderValidationResponseDto orderValidationResponseDto = p.mapTo(OrderValidationResponseDto.class);
    Log.info("Recieved order validation Response:" + orderValidationResponseDto);
    service.completeOrder(orderValidationResponseDto);
  }
}
