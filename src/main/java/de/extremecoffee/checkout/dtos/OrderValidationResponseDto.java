package de.extremecoffee.checkout.dtos;

public record OrderValidationResponseDto(Boolean isValid, Double subTotal, Long id, String message) {
}
