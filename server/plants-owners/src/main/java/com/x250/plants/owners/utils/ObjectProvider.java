package com.x250.plants.owners.utils;

import com.x250.plants.owners.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public class ObjectProvider {

    private ObjectProvider() {
        // empty constructor
    }

    public static <T, V, W extends JpaRepository<T, V>> T getObjectFromDB(V objectId, W repository)
            throws EntityNotFoundException {
        log.debug("getObjectFromDB({}, {})", objectId, repository);
        return repository.findById(objectId)
                .orElseThrow(() -> new EntityNotFoundException("Object: " + objectId + " does not exist in database"));
    }
}
