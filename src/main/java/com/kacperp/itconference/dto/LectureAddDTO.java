package com.kacperp.itconference.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LectureAddDTO {

	@NotNull
	private Long conferenceId;
	@NotBlank
	private String name;
	@NotBlank
	private String lecturer;
	@NotBlank
	private String category;
	@NotNull
	private LocalDateTime startTime;
	@NotNull
	private LocalDateTime endTime;
	@NotNull
	private Integer places;

	public LectureAddDTO() {
	}

	public LectureAddDTO(Long conferenceId, String name, String lecturer, String category, LocalDateTime startTime,
			LocalDateTime endTime, Integer places) {
		this.conferenceId = conferenceId;
		this.name = name;
		this.lecturer = lecturer;
		this.category = category;
		this.startTime = startTime;
		this.endTime = endTime;
		this.places = places;
	}

	public Long getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}

}
