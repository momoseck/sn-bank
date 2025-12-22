package sn.bank.common.event;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferCompletedEvent(
        String transferId,
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        Instant occurredAt,
        String initiatedBy // subject/user id
) {}

