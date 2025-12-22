package sn.bank.transfertservice.infra.client;

import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "${clients.account-service.url}")
public interface AccountClient {

    @PostMapping("/api/accounts/{id}/debit")
    void debit(@PathVariable("id") Long id, @RequestParam("amount") BigDecimal amount);
    @PostMapping("/api/accounts/{id}/credit")
    void credit(@PathVariable("id") Long id, @RequestParam("amount") BigDecimal amount);
}
