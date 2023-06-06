package com.kacperp.itconference.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
