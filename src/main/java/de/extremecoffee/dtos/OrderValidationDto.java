package de.extremecoffee.dtos;

public record OrderValidationDto(Boolean isValid, Double subTotal, Long id) {
}
