package de.extremecoffee;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Checkout API", version = "1.0.0"), servers = @Server(url = "/api/checkout-service"))

public class CheckoutApplications extends Application {
}
