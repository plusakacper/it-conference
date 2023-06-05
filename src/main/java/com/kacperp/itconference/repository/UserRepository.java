package com.kacperp.itconference.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kacperp.itconference.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT u FROM User u WHERE u.username = :username")
	public Optional<User> findByUsername(@Param("username") String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

}
