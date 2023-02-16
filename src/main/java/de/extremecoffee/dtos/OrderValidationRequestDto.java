package de.extremecoffee.dtos;

public record OrderValidationRequestDto(ItemToValidateDto[] items, Long id) {
}
