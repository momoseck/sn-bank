package sn.bank.accountservice.infra;


import sn.bank.accountservice.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIban(String iban);
}
