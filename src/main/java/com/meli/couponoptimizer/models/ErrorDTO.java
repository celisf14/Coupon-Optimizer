package com.meli.couponoptimizer.models;

import lombok.Builder;
import lombok.Data;

/**
 * La clase representa un objeto de transferencia de datos que contiene información
 * sobre un error ocurrido durante el procesamiento de una petición.
 */
@Data
@Builder
public class ErrorDTO {
  private String code;
  private String message;
  private String idLog;
}