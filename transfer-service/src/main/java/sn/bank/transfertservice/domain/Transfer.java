package sn.bank.transfertservice.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transfer")
public class Transfer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="transfer_id", nullable=false, unique=true, length=64)
    private String transferId;

    @Column(name="from_account_id", nullable=false)
    private Long fromAccountId;

    @Column(name="to_account_id", nullable=false)
    private Long toAccountId;

    @Column(nullable=false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name="created_at", nullable=false)
    private Instant createdAt;

    @Column(name="initiated_by", nullable=false, length=128)
    private String initiatedBy;

    protected Transfer() {}

    public Transfer(String transferId, Long from, Long to, BigDecimal amount, Instant createdAt, String initiatedBy) {
        this.transferId = transferId;
        this.fromAccountId = from;
        this.toAccountId = to;
        this.amount = amount;
        this.createdAt = createdAt;
        this.initiatedBy = initiatedBy;
    }

    public String getTransferId() { return transferId; }
    public Long getFromAccountId() { return fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
    public String getInitiatedBy() { return initiatedBy; }
}

