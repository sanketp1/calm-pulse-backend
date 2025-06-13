package com.neocortex.controllers;

import com.neocortex.payloads.resource.CreateResourceRequest;
import com.neocortex.payloads.resource.ResourceResponse;
import com.neocortex.payloads.resource.UpdateResourceRequest;
import com.neocortex.services.IResourcesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
@Slf4j
public class ResourcesController {

    private final IResourcesService resourcesService;

    // READ endpoints - accessible by all authenticated users (including ROLE_USER)
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(@PathVariable Long id) {
        log.info("Fetching resource with id {}", id);
        var resource = resourcesService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources() {
        log.info("Fetching all resources");
        var resources = resourcesService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    // CREATE, UPDATE, DELETE - accessible only by admins
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(@Valid @RequestBody CreateResourceRequest request) {
        log.info("Admin creating resource");
        var resource = resourcesService.createResource(request);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> updateResource(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateResourceRequest request) {
        log.info("Admin updating resource id {}", id);
        var updated = resourcesService.updateResource(id, request);
        return ResponseEntity.ok(updated);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        log.info("Admin deleting resource id {}", id);
        resourcesService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteMultipleResources(@RequestBody List<Long> ids) {
        log.info("Admin deleting multiple resources {}", ids);
        resourcesService.deleteMultipleResources(ids);
        return ResponseEntity.noContent().build();
    }
}
