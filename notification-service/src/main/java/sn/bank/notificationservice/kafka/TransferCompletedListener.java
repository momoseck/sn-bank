package sn.bank.notificationservice.kafka;

import sn.bank.common.event.TransferCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransferCompletedListener {

    private static final Logger log = LoggerFactory.getLogger(TransferCompletedListener.class);

    @KafkaListener(topics = "transfer.completed")
    public void onEvent(TransferCompletedEvent event) {
        log.info("NOTIF: transferId={} amount={} from={} to={} by={}",
                event.transferId(), event.amount(), event.fromAccountId(), event.toAccountId(), event.initiatedBy());
    }
}
