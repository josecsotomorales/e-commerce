package com.jose.ecommerce.model.persistence.repositories;

import com.jose.ecommerce.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username)
}
