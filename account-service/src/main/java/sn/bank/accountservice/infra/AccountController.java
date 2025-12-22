package sn.bank.accountservice.infra;

import sn.bank.accountservice.domain.Account;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountRepository repo;

    public AccountController(AccountRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("account not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Account create(@RequestParam @NotNull String iban, @RequestParam @NotNull BigDecimal balance) {
        return repo.save(new Account(iban, balance));
    }

    @PostMapping("/{id}/debit")
    public void debit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account acc = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("account not found"));
        acc.debit(amount);
        repo.save(acc);
    }

    @PostMapping("/{id}/credit")
    public void credit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account acc = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("account not found"));
        acc.credit(amount);
        repo.save(acc);
    }
}

