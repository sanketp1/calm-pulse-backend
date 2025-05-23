package com.neocortex.repositories;

import com.neocortex.models.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resources,Long> {
}
