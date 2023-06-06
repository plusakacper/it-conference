package com.kacperp.itconference.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lectures")
public class Lecture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lecturer;
	private String category;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer places;
	private Integer freePlaces;
	@ManyToOne
	private Conference conference;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(Integer freePlaces) {
		this.freePlaces = freePlaces;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, endTime, freePlaces, id, lecturer, name, places, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lecture other = (Lecture) obj;
		return Objects.equals(category, other.category) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(freePlaces, other.freePlaces) && Objects.equals(id, other.id)
				&& Objects.equals(lecturer, other.lecturer) && Objects.equals(name, other.name)
				&& Objects.equals(places, other.places) && Objects.equals(startTime, other.startTime);
	}

	@Override
	public String toString() {
		return "Lecture [id=" + id + ", name=" + name + ", lecturer=" + lecturer + ", category=" + category
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", places=" + places + ", freePlaces="
				+ freePlaces + "]";
	}

}
