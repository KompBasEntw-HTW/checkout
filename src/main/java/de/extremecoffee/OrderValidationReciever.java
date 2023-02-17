package de.extremecoffee;

import de.extremecoffee.checkout.CoffeeOrder;
import de.extremecoffee.dtos.OrderValidationDto;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderValidationReciever {
  @Incoming("order-validation")
  @Transactional
  @Blocking
  public void recieveOrderValidation(JsonObject p) {
    OrderValidationDto orderValidationDto = p.mapTo(OrderValidationDto.class);

    Log.info("Recieved order validation Response:" + orderValidationDto);

    CoffeeOrder coffeeOrder = CoffeeOrder.findById(orderValidationDto.id());
    if (orderValidationDto.isValid()) {
      coffeeOrder.subTotal = orderValidationDto.subTotal();
      coffeeOrder.valid = true;
    } else {
      coffeeOrder.subTotal = 0.0;
      coffeeOrder.valid = false;
      coffeeOrder.canceled = true;
    }
  }
}
