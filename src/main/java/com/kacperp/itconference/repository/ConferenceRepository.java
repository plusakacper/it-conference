package com.kacperp.itconference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kacperp.itconference.domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}
