package com.meli.couponoptimizer.exceptions;

import lombok.Data;

@Data
public class CouponOptimizerException extends RuntimeException {

  private String code;

  /**
   * Constructor para crear una nueva excepción con un mensaje y un código de error específico.
   * @param code Código de error.
   * @param message Mensaje de error.
   */
  public CouponOptimizerException(String code, String message) {
    super(message);
    this.code = code;
  }
}
