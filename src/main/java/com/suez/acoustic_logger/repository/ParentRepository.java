package com.suez.acoustic_logger.repository;

import com.suez.acoustic_logger.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path= "/parents")
public interface ParentRepository  extends JpaRepository<Parent, String> {
}
