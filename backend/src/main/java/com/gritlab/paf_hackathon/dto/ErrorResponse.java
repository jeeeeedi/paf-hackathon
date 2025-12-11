package com.gritlab.paf_hackathon.dto;

public record ErrorResponse(
    String code,
    String message,
    String details
) {}
