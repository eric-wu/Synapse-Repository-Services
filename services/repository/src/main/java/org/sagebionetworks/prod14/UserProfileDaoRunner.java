package org.sagebionetworks.prod14;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sagebionetworks.repo.model.UserGroup;
import org.sagebionetworks.repo.model.UserGroupDAO;
import org.sagebionetworks.repo.model.UserProfile;
import org.sagebionetworks.repo.model.UserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.bytecode.opencsv.CSVWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dao-beans.spb.xml" })
public class UserProfileDaoRunner {

	@Autowired
	private UserGroupDAO userGroupDao;

	@Autowired
	private UserProfileDAO userProfileDao;

	@Test
	public void runUserProfile() throws Exception {

		// Get all the user groups
		final boolean isIndividual = true;
		final Collection<UserGroup> ugList = userGroupDao.getAll(isIndividual);
		final Map<String, UserGroup> ugMap = new HashMap<String, UserGroup>();
		for (UserGroup ug : ugList) {
			ugMap.put(ug.getId(), ug);
		}

		// Get all the user profiles
		final List<UserProfile> upList = userProfileDao.getInRange(0L, Long.MAX_VALUE);
		final Map<String, UserProfile> upMap = new HashMap<String, UserProfile>();
		for (UserProfile up : upList) {
			upMap.put(up.getOwnerId(), up);
		}

		// Now merge the two lists into a list of users
		final List<User> userList = new ArrayList<User>();
		for (UserProfile userProfile : upList) {
			String id = userProfile.getOwnerId();
			UserGroup ug = ugMap.get(id);
			String userName = ug.getName();
			String email = userProfile.getEmail();
			String firstName = userProfile.getFirstName();
			String lastName = userProfile.getLastName();
			boolean isSage = isSage(userName, email);
			User user = new User(id, userName, email, firstName, lastName, isSage);
			userList.add(user);
		}
		for (UserGroup ug : ugList) {
			if (!upMap.containsKey(ug.getId())) {
				String id = ug.getId();
				String userName = ug.getName();
				boolean isSage = isSage(userName, null);
				User user = new User(id, userName, null, null, null, isSage);
				userList.add(user);
			}
		}

		// Find more sage employees
		// If the user has the same first name and last name
		final Set<String> sageSet = new HashSet<String>();
		for (User user : userList) {
			if (user.isSage) {
				final String key = getUserKey(user);
				sageSet.add(key);
			}
		}
		for (User user : userList) {
			final String key = getUserKey(user);
			if (!user.isSage && sageSet.contains(key)) {
				user.setSage(true);
			}
		}

		List<String[]> outputList = new ArrayList<String[]>();
		for (User user : userList) {
			String[] output = new String[6];
			output[0] = user.getId();
			output[1] = user.getUserName();
			output[2] = user.getEmail();
			output[3] = user.getFirstName();
			output[4] = user.getLastName();
			output[5] = Boolean.toString(user.isSage());
			outputList.add(output);
		}

		CSVWriter writer = new CSVWriter(new FileWriter("/Users/ewu/user-profile.csv"));
		writer.writeAll(outputList);
		writer.close();
	}

	private boolean isSage(String userName, String email) {
		boolean isSage = false;
		if (userName != null) {
			if (userName.toLowerCase().endsWith("@sagebase.org")) {
				isSage = true;
			}
		}
		if (!isSage && email != null) {
			if (email.toLowerCase().endsWith("@sagebase.org")) {
				isSage = true;
			}
		}
		return isSage;
	}

	private String getUserKey(User user) {
		final String separator = "|||";
		String key = separator;
		if (user.getFirstName() != null) {
			key = key + user.getFirstName().toLowerCase();
		}
		key = key + separator;
		if (user.getLastName() != null) {
			key = key + user.getLastName().toLowerCase();
		}
		key = key + separator;
		return key;
	}

	private static class User {

		private User(String id, String userName, String email,
				String firstName, String lastName, boolean isSage) {
			this.id = id;
			this.userName = userName;
			this.email = email;
			this.firstName = firstName;
			this.lastName = lastName;
			this.isSage = isSage;
		}
		private String getId() {
			return id;
		}
		private String getUserName() {
			return userName;
		}
		private String getEmail() {
			return email;
		}
		private String getFirstName() {
			return firstName;
		}
		private String getLastName() {
			return lastName;
		}
		private boolean isSage() {
			return isSage;
		}
		private void setSage(boolean isSage) {
			this.isSage = isSage;
		}

		private final String id;
		private final String userName;
		private final String email;
		private final String firstName;
		private final String lastName;
		private boolean isSage;
	}
}
