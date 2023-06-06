package com.kacperp.itconference.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kacperp.itconference.dto.ChangeEmailDTO;
import com.kacperp.itconference.exception.UserException;
import com.kacperp.itconference.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}

	@PatchMapping("/user/{id}/email")
	public ResponseEntity<?> updateEmail(Principal principal, @RequestBody @Valid ChangeEmailDTO dto) {
		try {
			userService.updateEmail(principal.getName(), dto.getEmail());
			return ResponseEntity.ok().build();
		} catch (UserException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
