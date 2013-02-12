package org.sagebionetworks.repo.manager.trash;

import org.sagebionetworks.StackConfiguration;

/**
 * Constants for the trash and trash cans.
 *
 * @author Eric Wu
 */
public class TrashConstants {

	/**
	 * The maximum number of entities that can be moved
	 * into the trash can at one time.
	 */
	public static final int MAX_TRASHABLE = StackConfiguration.getTrashCanMaxTrashable();

	/**
	 * The path to the bootstrapped trash folder.
	 */
	public static final String TRASH_FOLDER_PATH = "/root/trash";
}