package com.meli.couponoptimizer.services.logException;

import com.meli.couponoptimizer.persistence.entity.LogErrorEntity;
import com.meli.couponoptimizer.persistence.repositories.LogErrorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;


@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {

  private final LogErrorRepository logErrorRepository;

  @Override
  public Long saveLog(Exception e) {
    LogErrorEntity logErrorEntity = LogErrorEntity.builder()
            .fecha(new Date())
            .aplicacion(getMethod(e))
            .traza(getTrace(e))
            .mensje(e.getMessage() == null ? "" : e.getMessage())
            .build();
    logErrorRepository.save(logErrorEntity);
    return logErrorEntity.getId();
  }


  /**
   * Obtiene el nombre del método que generó una excepción.
   *
   * @param e La excepción para la cual se desea obtener el nombre del método.
   * @return El nombre del método que generó la excepción.
   */
  public String getMethod(Exception e) {
    StackTraceElement[] stackTrace = e.getStackTrace();
    StackTraceElement methodThatThrewException = stackTrace[0];
    String method = methodThatThrewException.getClassName();
    method += " - " + methodThatThrewException.getMethodName();
    method += " Line: [" + Integer.toString(methodThatThrewException.getLineNumber()) + "]";
    return method;
  }


  /**
   * Obtiene la traza que generó una excepción.
   *
   * @param e La excepción para la cual se desea obtener el nombre del método.
   * @return El nombre del método que generó la excepción.
   */
  public String getTrace(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }


}
