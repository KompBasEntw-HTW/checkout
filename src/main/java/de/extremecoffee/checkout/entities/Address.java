package de.extremecoffee.checkout.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Address extends PanacheEntityBase {
  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid")
  @Schema(readOnly = true)
  public UUID id;
  public String firstName;
  public String lastName;
  public String street;
  public String streetNumber;
  public String additionalInformation;
  public String city;
  public String country;
  public String state;
  public String postalCode;
  public String userName;

  public static List<Address> getUserAddresses(String userName) {
    return list("userName", userName);
  }
}
