package org.sagebionetworks.prod14;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
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
	private UserProfileDAO userProfileDao;

	@Test
	public void runUserProfile() throws Exception {
		List<String[]> profiles = new ArrayList<String[]>();
		List<UserProfile> users = userProfileDao.getInRange(0L, Long.MAX_VALUE);
		for (UserProfile user : users) {
			String[] profile = new String[6];
			profile[0] = user.getOwnerId();
			profile[1] = user.getEmail();
			profile[2] = user.getUserName();
			profile[3] = user.getDisplayName();
			profile[4] = user.getFirstName();
			profile[5] = user.getLastName();
			profiles.add(profile);
		}
		CSVWriter writer = new CSVWriter(new FileWriter("/Users/ewu/user-profile.csv"));
		writer.writeAll(profiles);
		writer.close();
	}
}
