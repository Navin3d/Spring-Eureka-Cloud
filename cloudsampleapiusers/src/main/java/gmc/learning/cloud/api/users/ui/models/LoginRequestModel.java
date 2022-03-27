package gmc.learning.cloud.api.users.ui.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestModel {
	
	private String email;
	private String password;

}
