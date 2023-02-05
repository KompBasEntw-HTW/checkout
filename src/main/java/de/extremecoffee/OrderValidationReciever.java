package de.extremecoffee;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import de.extremecoffee.dtos.OrderValidationDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import io.quarkus.logging.Log;

@ApplicationScoped
public class OrderValidationReciever {
  @Incoming("order-validation")
  @Transactional
  public void recieveOrderValidation(OrderValidationDto orderValidationDto) {
    Log.info("Recieved order validation Response:" + orderValidationDto);
  }
}
