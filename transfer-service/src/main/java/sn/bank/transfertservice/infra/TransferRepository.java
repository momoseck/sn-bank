package sn.bank.transfertservice.infra;


import sn.bank.transfertservice.domain.Transfer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findByTransferId(String transferId);
}
