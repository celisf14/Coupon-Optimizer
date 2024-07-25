package com.meli.couponoptimizer.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "log_error")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogErrorEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Date fecha;

  @Column(nullable = false)
  private String aplicacion;

  @Column(nullable = false)
  private String traza;

  @Column
  private String mensje;
}
