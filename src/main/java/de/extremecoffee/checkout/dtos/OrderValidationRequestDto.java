package de.extremecoffee.checkout.dtos;

public record OrderValidationRequestDto(ItemToValidateDto[] items, Long id) {
}
