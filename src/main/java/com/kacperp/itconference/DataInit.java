package com.kacperp.itconference;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kacperp.itconference.domain.Role;
import com.kacperp.itconference.repository.RoleRepository;

@Configuration
public class DataInit {

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			Optional<Role> findByName = roleRepository.findByName("USER");
			if (findByName.isEmpty()) {
				Role role = new Role();
				role.setName(Constant.DEFAULT_USER_ROLE);
				roleRepository.save(role);
			}

		};
	}

}
