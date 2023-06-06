package com.kacperp.itconference.dto;

import java.time.LocalDateTime;

public class ReportByLectureDTO {

	private Long lectureId;
	private String name;
	private String lecturer;
	private String category;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Double attendance;

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
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

	public Double getAttendance() {
		return attendance;
	}

	public void setAttendance(Double attendance) {
		this.attendance = attendance;
	}

}
