package com.kacperp.itconference.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class NotificationHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private LocalDateTime dateOfJoining;
	private Long lectureId;
	private boolean isMailSend;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDateTime dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public boolean isMailSend() {
		return isMailSend;
	}

	public void setMailSend(boolean isMailSend) {
		this.isMailSend = isMailSend;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfJoining, email, id, isMailSend, lectureId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationHistory other = (NotificationHistory) obj;
		return Objects.equals(dateOfJoining, other.dateOfJoining) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && isMailSend == other.isMailSend
				&& Objects.equals(lectureId, other.lectureId) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "NotificationHistory [id=" + id + ", username=" + username + ", email=" + email + ", dateOfJoining="
				+ dateOfJoining + ", lectureId=" + lectureId + ", isMailSend=" + isMailSend + "]";
	}

}
