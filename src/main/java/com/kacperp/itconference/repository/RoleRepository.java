package com.kacperp.itconference.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kacperp.itconference.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Optional<Role> findByName(String name);

}
