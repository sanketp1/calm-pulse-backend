package com.neocortex.services;

import com.neocortex.payloads.CreateResourceRequest;
import com.neocortex.payloads.UpdateResourceRequest;
import com.neocortex.payloads.ResourceResponse;

import java.util.List;

/**
 * Service interface for managing resources in the application.
 * <p>
 * This interface defines methods for creating, retrieving, updating, and deleting resources,
 * as well as handling bulk operations. It provides a contract for resource management
 * functionality within the system.
 * </p>
 * <p>Key methods:</p>
 * <ul>
 *   <li><b>createResource</b>: Creates a new resource.</li>
 *   <li><b>getResourceById</b>: Retrieves a resource by its unique identifier.</li>
 *   <li><b>getAllResources</b>: Fetches all resources in the system.</li>
 *   <li><b>updateResource</b>: Updates an existing resource.</li>
 *   <li><b>deleteResource</b>: Deletes a resource by its unique identifier.</li>
 *   <li><b>deleteMultipleResources</b>: Deletes multiple resources by their unique identifiers.</li>
 * </ul>
 */
public interface IResourcesService {

    /**
     * Creates a new resource.
     * <p><b>Admin-only operation.</b></p>
     *
     * @param createResourceRequest the request object containing the details of the resource to be created
     * @return a {@link ResourceResponse} object containing the details of the created resource
     */
    ResourceResponse createResource(CreateResourceRequest createResourceRequest);

    /**
     * Retrieves a specific resource by its ID.
     * <p><b>Accessible to all users.</b></p>
     *
     * @param resourceId the unique identifier of the resource to retrieve
     * @return a {@link ResourceResponse} object containing the details of the retrieved resource
     */
    ResourceResponse getResourceById(Long resourceId);

    /**
     * Fetches all resources in the system.
     * <p><b>Accessible to all users.</b></p>
     *
     * @return a list of {@link ResourceResponse} objects containing the details of all resources
     */
    List<ResourceResponse> getAllResources();

    /**
     * Updates an existing resource.
     * <p><b>Admin-only operation.</b></p>
     *
     * @param resourceId            the unique identifier of the resource to update
     * @param updateResourceRequest the request object containing the updated details of the resource
     * @return a {@link ResourceResponse} object containing the details of the updated resource
     */
    ResourceResponse updateResource(Long resourceId, UpdateResourceRequest updateResourceRequest);

    /**
     * Deletes a specific resource by its ID.
     * <p><b>Admin-only operation.</b></p>
     *
     * @param resourceId the unique identifier of the resource to delete
     */
    void deleteResource(Long resourceId);

    /**
     * Deletes multiple resources by their IDs.
     * <p><b>Admin-only operation.</b></p>
     *
     * @param resourceIds a list of unique identifiers of the resources to delete
     */
    void deleteMultipleResources(List<Long> resourceIds);
}
