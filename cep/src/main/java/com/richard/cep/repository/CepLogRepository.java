package com.richard.cep.repository;


import com.richard.cep.model.CepLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CepLogRepository extends JpaRepository<CepLog, Long> {
}
