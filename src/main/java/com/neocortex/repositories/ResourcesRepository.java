package com.neocortex.repositories;

import com.neocortex.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resource,Long> {
}
