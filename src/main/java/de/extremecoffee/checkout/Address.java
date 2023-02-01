package de.extremecoffee.checkout;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Address extends PanacheEntityBase {
  @Schema(readOnly = true)
  @Id
  @GeneratedValue
  public Long id;
  public String firstName;
  public String lastName;
  public String street;
  public Integer streetNumber;
  public String additionalInformation;
  public String city;
  public String country;
  public String state;
  public String postalCode;
}
