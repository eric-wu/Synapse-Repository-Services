package org.sagebionetworks.repo.model.dbo.persistence.table;

/**
 * These are the states that a table can be in.
 * 
 * @author John
 *
 */
public enum TableStateEnum {
    AVAILABLE,
    PROCESSING,
    PROCESSING_FAILED;
}
