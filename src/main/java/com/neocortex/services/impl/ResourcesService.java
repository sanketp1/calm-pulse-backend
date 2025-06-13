package com.neocortex.services.impl;

import com.neocortex.exceptions.ResourceNotFoundException;
import com.neocortex.models.Resource;
import com.neocortex.payloads.resource.CreateResourceRequest;
import com.neocortex.payloads.resource.ResourceResponse;
import com.neocortex.payloads.resource.UpdateResourceRequest;
import com.neocortex.repositories.ResourcesRepository;
import com.neocortex.services.IResourcesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ResourcesService implements IResourcesService {

    private final ResourcesRepository resourceRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResourceResponse createResource(CreateResourceRequest createResourceRequest) {
        Resource resource = modelMapper.map(createResourceRequest, Resource.class);
        Resource saved = resourceRepository.save(resource);
        log.info("Resource created with ID: {}", saved.getId());
        return modelMapper.map(saved, ResourceResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceResponse getResourceById(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + resourceId));
        log.info("Fetched resource with ID: {}", resourceId);
        return modelMapper.map(resource, ResourceResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceResponse> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        log.info("Fetched all resources, count: {}", resources.size());
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResourceResponse updateResource(Long resourceId, UpdateResourceRequest updateResourceRequest) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + resourceId));

        if (updateResourceRequest.getTitle() != null) {
            resource.setTitle(updateResourceRequest.getTitle());
        }
        if (updateResourceRequest.getDescription() != null) {
            resource.setDescription(updateResourceRequest.getDescription());
        }
        if (updateResourceRequest.getTags() != null) {
            resource.setTags(updateResourceRequest.getTags());
        }
        if (updateResourceRequest.getDuration() != null) {
            resource.setDuration(updateResourceRequest.getDuration());
        }
        if (updateResourceRequest.getInstructions() != null) {
            resource.setInstructions(updateResourceRequest.getInstructions());
        }
        if (updateResourceRequest.getBenefits() != null) {
            resource.setBenefits(updateResourceRequest.getBenefits());
        }
        if (updateResourceRequest.getAttachments() != null) {
            resource.setAttachments(updateResourceRequest.getAttachments());
        }

        Resource updated = resourceRepository.save(resource);
        log.info("Resource updated with ID: {}", resourceId);
        return modelMapper.map(updated, ResourceResponse.class);
    }

    @Override
    public void deleteResource(Long resourceId) {
        if (!resourceRepository.existsById(resourceId)) {
            log.warn("Attempted to delete non-existing resource with ID: {}", resourceId);
            throw new ResourceNotFoundException("Resource not found with id " + resourceId);
        }
        resourceRepository.deleteById(resourceId);
        log.info("Deleted resource with ID: {}", resourceId);
    }

    @Override
    public void deleteMultipleResources(List<Long> resourceIds) {
        List<Resource> resources = resourceRepository.findAllById(resourceIds);
        if (resources.size() != resourceIds.size()) {
            log.warn("One or more resources not found for IDs: {}", resourceIds);
            throw new ResourceNotFoundException("One or more resources not found for given IDs");
        }
        resourceRepository.deleteAll(resources);
        log.info("Deleted multiple resources with IDs: {}", resourceIds);
    }
}
