package org.everbuild.celestia.orion.platform.minestom.api.raycast

/**
 * The result of the [RayCast.castRay].
 *
 * Entity if it hits an entity, block if the conditional fails, and none if no entities/blocks were found.
 */
enum class HitType {
    ENTITY,
    BLOCK,
    NONE;
}