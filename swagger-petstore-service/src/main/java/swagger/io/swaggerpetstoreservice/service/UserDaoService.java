package swagger.io.swaggerpetstoreservice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import swagger.io.swaggerpetstoreservice.common.response.CommonResponse;
import swagger.io.swaggerpetstoreservice.common.response.ResponseCodes;
import swagger.io.swaggerpetstoreservice.common.response.UserNotFoundException;
import swagger.io.swaggerpetstoreservice.model.User;

@Component
public class UserDaoService {
	private static List<User> userList = new ArrayList<>();
	private static int userCount = 0;
	static {
		userList.add(new User(++userCount, "Shreyaa_14", "Shreya", "Yadav", "abc@gmail.com", "qqqqqq", "1234567890", 1));
		userList.add(new User(++userCount, "Jai_05", "Jai", "Yadav", "def@gmail.com", "abbsbs", "1234567890", 1));
		userList.add(new User(++userCount, "Nidhi_13", "Nidhi", "Yadav", "ghi@gmail.com", "hhvsbsj", "1234567890", 1));
		userList.add(new User(++userCount, "Neena_03", "Neena", "Yadav", "jkl@gmail.com", "hsjahsiaj", "1234567890", 1));
	}
	
	public List<User> findAllUsers() {
		return userList;
	}
	
	public User findUserById(int id) {
		for(User it : userList) {
			if(it.getId() == id) return it;
		}
		return null;
	}
	
	public CommonResponse createUser(User user) {
		User existingUser = findUserByUsername(user.getUserName());
		if(existingUser != null) {
			return ResponseCodes.getErrorResponse(ResponseCodes.ALREADY_EXIST, "Username already exist");
		}
		user.setId(++userCount);
		userList.add(user);
		return ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");
	}

	public void deleteUserById(int id) {
		Iterator<User> iterator = userList.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return;
			}
		}
	}

	public User findUserByUsername(String username) {
		for(User it : userList) {
			if(it.getUserName() != null && it.getUserName().equals(username)) return it;
		}
		return null;
	}

	public void deleteUserByUsername(final String username) {
		Iterator<User> iterator = userList.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getUserName() != null && user.getUserName().equals(username)) {
				iterator.remove();
				return;
			}
		}
		
	}
	
	public CommonResponse updateUser(final User user, final String username) {
		User existingUser = findUserByUsername(username);
		if(existingUser == null) {
			throw new UserNotFoundException("User not found");
		}
		deleteUserByUsername(username);
		createUser(user);
		return ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");
	}
	
	public CommonResponse deleteUser(final String username) {
		User existingUser = findUserByUsername(username);
		if(existingUser == null) {
			throw new UserNotFoundException("User not found");
		}
		deleteUserByUsername(existingUser.getUserName());
		return ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");
	}
	
	
}
