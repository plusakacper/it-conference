package com.kacperp.itconference.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kacperp.itconference.domain.Conference;
import com.kacperp.itconference.domain.Lecture;
import com.kacperp.itconference.dto.LectureAddDTO;
import com.kacperp.itconference.exception.ConferenceException;
import com.kacperp.itconference.repository.ConferenceRepository;
import com.kacperp.itconference.repository.LectureRepository;
import com.kacperp.itconference.repository.UserRepository;

@Service
public class ConferenceService {

	private final ConferenceRepository conferenceRepository;
	private final LectureRepository lectureRepository;
	private final UserRepository userRepository;

	public ConferenceService(ConferenceRepository conferenceRepository, LectureRepository lectureRepository,
			UserRepository userRepository) {
		this.conferenceRepository = conferenceRepository;
		this.lectureRepository = lectureRepository;
		this.userRepository = userRepository;
	}

	public void addLecture(LectureAddDTO dto) throws ConferenceException {
		Conference conference = conferenceRepository.findById(dto.getConferenceId())
				.orElseThrow(() -> new ConferenceException("CONFERENCE NOT FOUND"));

		if (dto.getStartTime().isBefore(conference.getStartTime())
				|| dto.getEndTime().isAfter(conference.getEndTime())) {
			throw new ConferenceException("WRONG DATE");
		}

		Lecture lecture = new Lecture();
		lecture.setCategory(dto.getCategory());
		lecture.setName(dto.getName());
		lecture.setLecturer(dto.getLecturer());
		lecture.setPlaces(dto.getPlaces());
		lecture.setFreePlaces(dto.getPlaces());
		lecture.setStartTime(dto.getStartTime());
		lecture.setEndTime(dto.getEndTime());
		lecture.setConference(conference);

		lectureRepository.save(lecture);

	}

	public void addLectures(List<LectureAddDTO> lectures) throws ConferenceException {
		for (LectureAddDTO lecture : lectures) {
			addLecture(lecture);
		}
	}

}
