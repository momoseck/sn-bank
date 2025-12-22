package sn.bank.transfertservice.app;

import sn.bank.common.event.TransferCompletedEvent;
import sn.bank.transfertservice.domain.Transfer;
import sn.bank.transfertservice.infra.TransferRepository;
import sn.bank.transfertservice.infra.client.AccountClient;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferUseCase {

    private final AccountClient accountClient;
    private final TransferRepository transferRepo;
    private final KafkaTemplate<String, TransferCompletedEvent> kafka;

    public TransferUseCase(AccountClient accountClient, TransferRepository transferRepo,
                           KafkaTemplate<String, TransferCompletedEvent> kafka) {
        this.accountClient = accountClient;
        this.transferRepo = transferRepo;
        this.kafka = kafka;
    }

    @Transactional
    public String transfer(Long fromId, Long toId, BigDecimal amount, Authentication auth) {
        if (fromId.equals(toId)) throw new IllegalArgumentException("from and to must differ");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");

        String transferId = UUID.randomUUID().toString();
        String initiatedBy = auth.getName();

        // Sync calls
        accountClient.debit(fromId, amount);
        accountClient.credit(toId, amount);

        Transfer entity = new Transfer(transferId, fromId, toId, amount, Instant.now(), initiatedBy);
        transferRepo.save(entity);

        // Async event
        TransferCompletedEvent event = new TransferCompletedEvent(
                transferId, fromId, toId, amount, Instant.now(), initiatedBy
        );
        kafka.send("transfer.completed", transferId, event);

        return transferId;
    }
}

