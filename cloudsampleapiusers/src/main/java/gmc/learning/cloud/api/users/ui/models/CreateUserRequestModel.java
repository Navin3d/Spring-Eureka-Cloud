package gmc.learning.cloud.api.users.ui.models;

import lombok.Data;

@Data
public class CreateUserRequestModel {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
}
