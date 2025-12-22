package sn.bank.transfertservice.infra;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sn.bank.common.web.ApiError;
import sn.bank.common.web.Correlation;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(RuntimeException ex, HttpServletRequest req) {
        String cid = req.getHeader(Correlation.HEADER);
        return new ApiError("BAD_REQUEST", ex.getMessage(), cid, Instant.now());
    }
}
