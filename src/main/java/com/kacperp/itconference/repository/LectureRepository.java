package com.kacperp.itconference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kacperp.itconference.domain.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
