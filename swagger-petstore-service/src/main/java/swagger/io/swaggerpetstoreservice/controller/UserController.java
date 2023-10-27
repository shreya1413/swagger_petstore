package swagger.io.swaggerpetstoreservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swagger.io.swaggerpetstoreservice.common.response.BadRequestException;
import swagger.io.swaggerpetstoreservice.common.response.CommonResponse;
import swagger.io.swaggerpetstoreservice.common.response.ResponseCodes;
import swagger.io.swaggerpetstoreservice.common.response.UserNotFoundException;
import swagger.io.swaggerpetstoreservice.model.User;
import swagger.io.swaggerpetstoreservice.service.UserDaoService;


@RestController
@RequestMapping(value = "/petstore.swagger.io/v2")
public class UserController {

	@Autowired
	private UserDaoService userService;
	
	@PostMapping(path = "/user")
	public CommonResponse createUser(@RequestBody User user) {
		if(user == null) {
			throw new BadRequestException("No information provided");
		}
		return userService.createUser(user);
		
	}
	
	@PutMapping(path = "/user/{username}")
	public CommonResponse updateUser(@PathVariable String username, @RequestBody User user) {
		if(username.isBlank()) {
			throw new BadRequestException("Invalid username supplied");
		}
		if(user == null) {
			throw new BadRequestException("No information provided");
		}
		return userService.updateUser(user, username);
	}
	
	@GetMapping(path = "/user/{username}")
	public User getUserByUsername(@PathVariable String username)throws Exception {
		if(username.isBlank()) {
			throw new BadRequestException("Invalid username supplied");
		}
		User existingUser = userService.findUserByUsername(username);
		if(existingUser == null) {
			throw new UserNotFoundException("User not found");
		}
		return existingUser;
	}
	
	@DeleteMapping(path = "/user/{username}")
	public CommonResponse deleteUser(@PathVariable String username){
		if(username.isBlank()) {
			throw new BadRequestException("Invalid username supplied");
		}
		return userService.deleteUser(username);
	}
	
	@GetMapping(path = "/all-user")
	public List<User> getAllUser() {
		return userService.findAllUsers();
	}
	
	
}
