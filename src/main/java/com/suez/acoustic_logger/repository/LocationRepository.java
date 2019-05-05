package com.suez.acoustic_logger.repository;

import com.suez.acoustic_logger.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path= "/locations")
public interface LocationRepository extends JpaRepository<Location, Long> {
}
