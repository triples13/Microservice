package com.xylem.hackathon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.xylem.hackathon.domain.Role;
import com.xylem.hackathon.domain.RoleName;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleApplicationEngineer);
}
