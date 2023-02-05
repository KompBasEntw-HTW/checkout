package de.extremecoffee.dtos;

import java.util.List;

public record OrderValidationRequestDto(List<ItemToValidateDto> items, Long id) {
}
