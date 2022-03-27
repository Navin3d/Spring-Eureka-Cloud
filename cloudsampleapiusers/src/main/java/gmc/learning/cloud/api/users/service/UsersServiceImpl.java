package gmc.learning.cloud.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gmc.learning.cloud.api.users.data.UserEntity;
import gmc.learning.cloud.api.users.data.UsersRepository;
import gmc.learning.cloud.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		usersRepository.save(userEntity);
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		
		UserEntity userEntity = usersRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new ModelMapper().map(userEntity, UserDto.class);
		
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity foundUser = usersRepository.findByUserId(userId);
		
		return null;
	}

}
