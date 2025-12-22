package sn.bank.accountservice.infra;

import sn.bank.common.web.ApiError;
import sn.bank.common.web.Correlation;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(RuntimeException ex, HttpServletRequest req) {
        String cid = req.getHeader(Correlation.HEADER);
        return new ApiError("BAD_REQUEST", ex.getMessage(), cid, Instant.now());
    }
}

