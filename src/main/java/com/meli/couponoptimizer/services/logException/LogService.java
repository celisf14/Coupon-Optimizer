package com.meli.couponoptimizer.services.logException;

/**
 * Esta clase se encarga de registrar errores en la base de
 *  datos y obtener información sobre la traza de la excepción.
 */
public interface LogService {

  /**
   * Guarda un registro de error en la base de datos.
   * @param e La excepción que se desea registrar.
   * @return Id del log guardado en base de datos.
   */
  Long saveLog(Exception e);
}
