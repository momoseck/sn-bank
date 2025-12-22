package sn.bank.accountservice.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 34)
    private String iban;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    protected Account() {
    }

    public Account(String iban, BigDecimal balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void debit(BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        if (balance.compareTo(amount) < 0) throw new IllegalStateException("insufficient funds");
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        balance = balance.add(amount);
    }
}
