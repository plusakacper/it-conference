package com.kacperp.itconference.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kacperp.itconference.domain.Conference;
import com.kacperp.itconference.domain.Lecture;
import com.kacperp.itconference.domain.NotificationHistory;
import com.kacperp.itconference.domain.User;
import com.kacperp.itconference.dto.ConferenceInfoDTO;
import com.kacperp.itconference.dto.LectureAddDTO;
import com.kacperp.itconference.dto.LectureInfoDTO;
import com.kacperp.itconference.dto.ReportByCategoryDTO;
import com.kacperp.itconference.dto.ReportByLectureDTO;
import com.kacperp.itconference.exception.ConferenceException;
import com.kacperp.itconference.exception.UserException;
import com.kacperp.itconference.repository.ConferenceRepository;
import com.kacperp.itconference.repository.LectureRepository;
import com.kacperp.itconference.repository.NotificationHistoryRepository;
import com.kacperp.itconference.repository.UserRepository;

@Service
@Transactional
public class ConferenceService {

	private final ConferenceRepository conferenceRepository;
	private final LectureRepository lectureRepository;
	private final UserRepository userRepository;
	private final NotificationHistoryRepository notificationHistoryRepository;

	public ConferenceService(ConferenceRepository conferenceRepository, LectureRepository lectureRepository,
			UserRepository userRepository, NotificationHistoryRepository notificationHistoryRepository) {
		this.conferenceRepository = conferenceRepository;
		this.lectureRepository = lectureRepository;
		this.userRepository = userRepository;
		this.notificationHistoryRepository = notificationHistoryRepository;
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

	public ConferenceInfoDTO getConferenceInfo(Long conferenceId) throws ConferenceException {
		Conference conference = conferenceRepository.findById(conferenceId)
				.orElseThrow(() -> new ConferenceException("CONFERENCE NOT FOUND"));

		List<Lecture> lectures = conference.getLectures();

		ConferenceInfoDTO conferenceInfoDTO = new ConferenceInfoDTO();
		conferenceInfoDTO.setName(conference.getName());
		conferenceInfoDTO.setStartTime(conference.getStartTime());
		conferenceInfoDTO.setEndTime(conference.getEndTime());

		List<LectureInfoDTO> lectureInfoDTOs = new ArrayList<>();
		for (Lecture l : lectures) {
			LectureInfoDTO lectureInfoDTO = new LectureInfoDTO();
			lectureInfoDTO.setConferenceName(conference.getName());
			lectureInfoDTO.setLectureName(l.getName());
			lectureInfoDTO.setLecturer(l.getLecturer());
			lectureInfoDTO.setStartTime(l.getStartTime());
			lectureInfoDTO.setEndTime(l.getEndTime());
			lectureInfoDTO.setCategory(l.getCategory());
			lectureInfoDTOs.add(lectureInfoDTO);
		}
		conferenceInfoDTO.setLectures(lectureInfoDTOs);

		return conferenceInfoDTO;
	}

	public void joinToLecture(Long lectureId, String username) throws UserException, ConferenceException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserException("USER NOT FOUND"));
		Lecture lecture = lectureRepository.findById(lectureId)
				.orElseThrow(() -> new ConferenceException("LECTURE NOT FOUND"));

		// Sprawdzamy czy sa wolne miejsca
		if (lecture.getFreePlaces() == 0) {
			throw new ConferenceException("NO PLACES AVAILABLE");
		}

		// Sprawdzamy czy uzytkownik nalezy juz do danego wykladu
		if (user.getLectures().contains(lecture)) {
			throw new ConferenceException("USER ALREADY BELONG TO LECTURE");
		}

		// Sprawdzamy czy w czasie wydarzenia na ktore zapisujemy, uzytkownik nie jest
		// juz zapisany do innego wydarzenia
		for (Lecture l : user.getLectures()) {
			if (lecture.getStartTime().isBefore(l.getEndTime()) && lecture.getEndTime().isAfter(l.getStartTime())) {
				throw new ConferenceException("AT THIS TIME YOU ARE ATTENDING ANOTHER LECTURE");
			}
		}

		// Aktualizujemy ilosc wolnych miejsc
		lecture.setFreePlaces(lecture.getFreePlaces() - 1);
		user.addLecture(lecture);

		userRepository.save(user);

		// Wysylamy do uzytkownika maila (zgodnie z poleceniem nie wysylania maila, a
		// jedynie pozostawiamy slad w historii)
		NotificationHistory notificationHistory = new NotificationHistory();
		notificationHistory.setUsername(user.getUsername());
		notificationHistory.setEmail(user.getEmail());
		notificationHistory.setDateOfJoining(LocalDateTime.now());
		notificationHistory.setLectureId(lecture.getId());
		// Majac informacje czy mail zostal wyslany mozemy pozniej stworzyc scheduler,
		// ktory np. sprawdzi czy sa w bazie niewyslane maile i je wysle
		notificationHistory.setMailSend(false);

		notificationHistoryRepository.save(notificationHistory);

	}

	public List<LectureInfoDTO> getUserLectures(String username) throws UserException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserException("USER NOT FOUND"));

		List<LectureInfoDTO> lectureInfoDTOs = new ArrayList<>();
		for (Lecture l : user.getLectures()) {
			LectureInfoDTO lectureInfoDTO = new LectureInfoDTO();
			lectureInfoDTO.setConferenceName(l.getConference().getName());
			lectureInfoDTO.setLectureName(l.getName());
			lectureInfoDTO.setLecturer(l.getLecturer());
			lectureInfoDTO.setStartTime(l.getStartTime());
			lectureInfoDTO.setEndTime(l.getEndTime());
			lectureInfoDTO.setCategory(l.getCategory());
			lectureInfoDTOs.add(lectureInfoDTO);
		}

		return lectureInfoDTOs;
	}

	public void cancelLecture(Long lectureId, String username) throws UserException, ConferenceException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserException("USER NOT FOUND"));
		Lecture lecture = lectureRepository.findById(lectureId)
				.orElseThrow(() -> new ConferenceException("LECTURE NOT FOUND"));

		// Sprawdzamy czy uzytkownik rzeczywscie jest zapisany do danego wykladu
		if (!user.getLectures().contains(lecture)) {
			throw new ConferenceException("USER DOESN'T BELONG TO THIS LECTURE");
		}

		// Zabezpieczenie przed wypisaniem sie z przeterminowanego wydarzenia
		if (lecture.getStartTime().isAfter(LocalDateTime.now())) {
			throw new ConferenceException("LECTURE HAS ALREADY STARTED");
		}

		// Aktualizujemy ilosc wolnych miejsc

		user.removeLecture(lecture);
		lecture.setFreePlaces(lecture.getFreePlaces() + 1);
		userRepository.save(user);

	}

	public List<ReportByLectureDTO> generateLectureRaport(Long id) throws ConferenceException {
		Conference conference = conferenceRepository.findById(id)
				.orElseThrow(() -> new ConferenceException("CONFERENCE NOT FOUND"));

		List<ReportByLectureDTO> report = new ArrayList<>();
		for (Lecture l : conference.getLectures()) {
			ReportByLectureDTO lecture = new ReportByLectureDTO();
			lecture.setLectureId(l.getId());
			lecture.setName(l.getName());
			lecture.setLecturer(l.getLecturer());
			lecture.setStartTime(l.getStartTime());
			lecture.setEndTime(l.getEndTime());
			lecture.setCategory(l.getCategory());
			lecture.setAttendance(((l.getPlaces() - l.getFreePlaces()) / (double) l.getPlaces()) * 100);
			report.add(lecture);
		}
		report.sort(Comparator.comparing(ReportByLectureDTO::getAttendance).reversed());

		return report;
	}

	public List<ReportByCategoryDTO> generateCategoryReport(Long id) throws ConferenceException {
		Conference conference = conferenceRepository.findById(id)
				.orElseThrow(() -> new ConferenceException("CONFERENCE NOT FOUND"));

		Map<String, List<Double>> map = new HashMap<>();

		for (Lecture l : conference.getLectures()) {
			Double value = ((l.getPlaces() - l.getFreePlaces()) / (double) l.getPlaces()) * 100;
			List<Double> list = map.get(l.getCategory());

			if (list == null) {
				List<Double> newList = new ArrayList<>();
				newList.add(value);
				map.put(l.getCategory(), newList);
			} else {

				list.add(value);

			}

		}
		List<ReportByCategoryDTO> report = new ArrayList<>();
		for (String key : map.keySet()) {
			List<Double> list = map.get(key);

			OptionalDouble average = list.stream().mapToDouble(a -> a).average();
			ReportByCategoryDTO category = new ReportByCategoryDTO();
			category.setCategory(key);
			category.setAttendance(average.getAsDouble());
			report.add(category);
		}

		report.sort(Comparator.comparing(ReportByCategoryDTO::getAttendance).reversed());
		return report;
	}

}
