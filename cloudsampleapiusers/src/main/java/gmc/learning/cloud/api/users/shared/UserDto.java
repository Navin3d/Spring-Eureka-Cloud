package gmc.learning.cloud.api.users.shared;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = -6771207141627558272L;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;
	private String encryptedPassword;

}
