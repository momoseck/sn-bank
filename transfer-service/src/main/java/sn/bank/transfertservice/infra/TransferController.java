package sn.bank.transfertservice.infra;


import sn.bank.transfertservice.app.TransferUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferUseCase useCase;

    public TransferController(TransferUseCase useCase) {
        this.useCase = useCase;
    }

    public record TransferRequest(
            @NotNull Long fromAccountId,
            @NotNull Long toAccountId,
            @NotNull @DecimalMin(value = "0.01") BigDecimal amount
    ) {}

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String create(@Valid @RequestBody TransferRequest req, Authentication auth) {
        return useCase.transfer(req.fromAccountId(), req.toAccountId(), req.amount(), auth);
    }
}

