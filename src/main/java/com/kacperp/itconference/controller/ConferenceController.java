package com.kacperp.itconference.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kacperp.itconference.exception.ConferenceException;
import com.kacperp.itconference.service.ConferenceService;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

	private final ConferenceService conferenceService;

	public ConferenceController(ConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	@GetMapping("/conference/{id}")
	public ResponseEntity<?> getInfo(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(conferenceService.getConferenceInfo(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/lecture/{id}/users")
	public ResponseEntity<?> joinToLecture(@PathVariable Long id, Principal principal) {
		try {
			conferenceService.joinToLecture(id, principal.getName());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

	@DeleteMapping("/lecture/{id}/users")
	public ResponseEntity<?> cancelLecture(@PathVariable Long id, Principal principal) {
		try {
			conferenceService.cancelLecture(id, principal.getName());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

	@GetMapping("/lectures/user")
	public ResponseEntity<?> getUserLectures(Principal principal) {
		try {

			return ResponseEntity.ok(conferenceService.getUserLectures(principal.getName()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

	@GetMapping("/{id}/lectures/report")
	public ResponseEntity<?> generateLectureReport(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(conferenceService.generateLectureRaport(id));
		} catch (ConferenceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}/category/report")
	public ResponseEntity<?> generateCategoryReport(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(conferenceService.generateCategoryReport(id));
		} catch (ConferenceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
