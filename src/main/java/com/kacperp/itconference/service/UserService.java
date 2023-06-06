package com.kacperp.itconference.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kacperp.itconference.Constant;
import com.kacperp.itconference.domain.Role;
import com.kacperp.itconference.domain.User;
import com.kacperp.itconference.dto.SignupDTO;
import com.kacperp.itconference.dto.UserInfoDTO;
import com.kacperp.itconference.exception.UserException;
import com.kacperp.itconference.repository.RoleRepository;
import com.kacperp.itconference.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserInfoDTO> getAll() {
		List<User> users = userRepository.findAll();
		List<UserInfoDTO> usersDTO = new ArrayList<>();

		for (User u : users) {
			UserInfoDTO userDTO = new UserInfoDTO();
			userDTO.setId(u.getId());
			userDTO.setUsername(u.getUsername());
			userDTO.setEmail(u.getEmail());
			usersDTO.add(userDTO);
		}

		return usersDTO;
	}

	public void registerUser(SignupDTO signupDTO) throws UserException {
		if (userRepository.existsByUsername(signupDTO.getUsername())) {
			throw new UserException("Username is already exists");
		}

		if (userRepository.existsByEmail(signupDTO.getEmail())) {
			throw new UserException("Email is already exists");
		}

		User user = new User(signupDTO.getUsername(), signupDTO.getEmail(),
				passwordEncoder.encode(signupDTO.getPassword()));

		Set<Role> roles = new HashSet<>();

		Optional<Role> userRole = roleRepository.findByName(Constant.DEFAULT_USER_ROLE);
		roles.add(userRole.get());

		user.setRoles(roles);
		userRepository.save(user);
	}

}
