package com.meli.couponoptimizer.exceptions;

import com.meli.couponoptimizer.models.ErrorDTO;
import com.meli.couponoptimizer.services.logException.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AllArgsConstructor
@RestControllerAdvice
public class ControllerAdvice {

  private LogService logService;

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ErrorDTO> RuntimeExceptionHandler(RuntimeException ex) {
    Long idLog = logService.saveLog(ex);
    ErrorDTO error = ErrorDTO.builder()
            .code("500")
            .message(ex.getMessage())
            .idLog(idLog.toString())
            .build();

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = CouponOptimizerException.class)
  public ResponseEntity<ErrorDTO> requestExceptionHandler(CouponOptimizerException ex) {
    Long idLog = logService.saveLog(ex);
    ErrorDTO error = ErrorDTO.builder()
            .code(ex.getCode())
            .message(ex.getMessage())
            .idLog(idLog.toString())
            .build();
    return new ResponseEntity<>(error, HttpStatusCode.valueOf(Integer.parseInt(ex.getCode())));
  }

}
