package com.kacperp.itconference;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kacperp.itconference.domain.Conference;
import com.kacperp.itconference.domain.Role;
import com.kacperp.itconference.domain.User;
import com.kacperp.itconference.dto.LectureAddDTO;
import com.kacperp.itconference.repository.ConferenceRepository;
import com.kacperp.itconference.repository.RoleRepository;
import com.kacperp.itconference.repository.UserRepository;
import com.kacperp.itconference.service.ConferenceService;

@Configuration
public class DataInit {

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder encoder, ConferenceRepository conferenceRepository, ConferenceService conferenceService) {
		return args -> {

			// Tworzymy podstawowa role usera
			Role role = new Role();
			role.setName(Constant.DEFAULT_USER_ROLE);
			Role savedRole = roleRepository.save(role);

			Set<Role> roles = new HashSet<>();
			roles.add(savedRole);

			// Tworzymy testowego usera
			User user1 = new User("user", "user@itconferenece.pl", encoder.encode("password"));
			user1.setRoles(roles);
			userRepository.save(user1);

			// Tworzymy testowego usera2
			User user2 = new User("user2", "user2@itconferenece.pl", encoder.encode("password"));
			user2.setRoles(roles);
			userRepository.save(user2);

			// Tworzymy konferencje zgodna z opisem z zadania
			Conference conference = new Conference();
			conference.setName("Konferencja informatyczna");
			conference.setStartTime(LocalDateTime.of(2023, Month.JUNE, 1, 10, 0));
			conference.setEndTime(LocalDateTime.of(2023, Month.JUNE, 1, 15, 45));
			Conference savedConference = conferenceRepository.save(conference);

			// Tworzymy przykladowe prelekcje, zgodnie z opisem po 3 rozne tematy na jedna
			// godzine
			LectureAddDTO lecture1_1 = new LectureAddDTO(conference.getId(), "Wykład o AI", "Jan Kowalski", "AI",
					LocalDateTime.of(2023, Month.JUNE, 1, 10, 0), LocalDateTime.of(2023, Month.JUNE, 1, 11, 45), 5);
			LectureAddDTO lecture1_2 = new LectureAddDTO(conference.getId(), "Wykład o Javie", "Jan Nowak",
					"Programowanie", LocalDateTime.of(2023, Month.JUNE, 1, 10, 0),
					LocalDateTime.of(2023, Month.JUNE, 1, 11, 45), 5);
			LectureAddDTO lecture1_3 = new LectureAddDTO(conference.getId(), "Wykład o BI", "Jan Norek",
					"Business Intelligence", LocalDateTime.of(2023, Month.JUNE, 1, 10, 0),
					LocalDateTime.of(2023, Month.JUNE, 1, 11, 45), 5);

			LectureAddDTO lecture2_1 = new LectureAddDTO(conference.getId(), "Wykład o Data Science", "Jan Kowalski",
					"AI", LocalDateTime.of(2023, Month.JUNE, 1, 12, 0), LocalDateTime.of(2023, Month.JUNE, 1, 13, 45),
					5);
			LectureAddDTO lecture2_2 = new LectureAddDTO(conference.getId(), "Wykład o React", "Jan Nowak",
					"Programowanie", LocalDateTime.of(2023, Month.JUNE, 1, 12, 0),
					LocalDateTime.of(2023, Month.JUNE, 1, 13, 45), 5);
			LectureAddDTO lecture2_3 = new LectureAddDTO(conference.getId(), "Wykład o Power BI", "Jan Norek",
					"Business Intelligence", LocalDateTime.of(2023, Month.JUNE, 1, 12, 0),
					LocalDateTime.of(2023, Month.JUNE, 1, 13, 45), 5);

			LectureAddDTO lecture3_1 = new LectureAddDTO(conference.getId(),
					"Wykład o algorytmach rozpoznawania twarzy", "Jan Kowalski", "AI",
					LocalDateTime.of(2023, Month.JUNE, 1, 14, 0), LocalDateTime.of(2023, Month.JUNE, 1, 15, 45), 5);
			LectureAddDTO lecture3_2 = new LectureAddDTO(conference.getId(), "Wykład o Spring Boot", "Jan Nowak",
					"Programowanie", LocalDateTime.of(2023, Month.JUNE, 1, 14, 0),
					LocalDateTime.of(2023, Month.JUNE, 1, 15, 45), 5);
			LectureAddDTO lecture3_3 = new LectureAddDTO(conference.getId(), "Wykład o SAP", "Jan Norek",
					"Business Intelligence", LocalDateTime.of(2023, Month.JUNE, 1, 14, 0),
					LocalDateTime.of(2023, Month.JUNE, 1, 15, 45), 5);

			List<LectureAddDTO> lectures = Arrays.asList(lecture1_1, lecture1_2, lecture1_3, lecture2_1, lecture2_2,
					lecture2_3, lecture3_1, lecture3_2, lecture3_3);
			conferenceService.addLectures(lectures);

			conferenceService.joinToLecture(1L, user1.getUsername());
			conferenceService.joinToLecture(4L, user1.getUsername());
			conferenceService.joinToLecture(2L, user2.getUsername());
			conferenceService.joinToLecture(5L, user2.getUsername());
		};
	}

}
