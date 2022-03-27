package gmc.learning.cloud.api.users.ui.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.cloud.api.users.service.UsersService;
import gmc.learning.cloud.api.users.shared.UserDto;
import gmc.learning.cloud.api.users.ui.models.CreateUserRequestModel;
import gmc.learning.cloud.api.users.ui.models.CreateUserResponseModel;
import gmc.learning.cloud.api.users.ui.models.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	private final UsersService usersService;
	private final Environment env;
	
	public UsersController(UsersService usersService, Environment env) {
		super();
		this.usersService = usersService;
		this.env = env;
	}

	@GetMapping("/status/check")
	public String status() {
		return "Working in port" + env.getProperty("local.server.port") + "Token " + env.getProperty("token.secret");
	}
	
	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto mappedUser = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = usersService.createUser(mappedUser);
		
		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue); 
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
		UserDto returnValue = usersService.getUserByUserId(userId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(returnValue);
	}

}
