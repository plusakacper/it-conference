package com.kacperp.itconference.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConferenceInfoDTO {

	private String name;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private List<LectureInfoDTO> lectures;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<LectureInfoDTO> getLectures() {
		return lectures;
	}

	public void setLectures(List<LectureInfoDTO> lectures) {
		this.lectures = lectures;
	}

}
