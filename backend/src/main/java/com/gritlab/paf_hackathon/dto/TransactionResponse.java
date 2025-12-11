package com.gritlab.paf_hackathon.dto;

import com.gritlab.paf_hackathon.model.Transaction.TransactionType;
import java.time.LocalDateTime;

public record TransactionResponse(
    String id,
    String playerName,
    TransactionType type,
    String reason,
    Double amount,
    Double balanceAfter,
    String referenceBetId,
    LocalDateTime createdAt
) {}