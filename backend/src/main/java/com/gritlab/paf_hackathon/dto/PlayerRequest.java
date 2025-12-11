package com.gritlab.paf_hackathon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@JsonIgnoreProperties(ignoreUnknown = false) // reject extra fields

public record PlayerRequest(
    @NotBlank
    String name,
    @NotNull
    @PositiveOrZero
    Number initialBalance
) {}
