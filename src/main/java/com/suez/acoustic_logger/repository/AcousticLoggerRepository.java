package com.suez.acoustic_logger.repository;

import com.suez.acoustic_logger.entity.AcousticLogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcousticLoggerRepository extends JpaRepository<AcousticLogger, String> {
    //AcousticLogger findAcousticLoggerByLocation_Id(Long id);
}
