package com.meli.couponoptimizer.persistence.repositories;

import com.meli.couponoptimizer.persistence.entity.LogErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogErrorRepository extends JpaRepository<LogErrorEntity, Long> {
}
