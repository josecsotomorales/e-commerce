package com.jose.ecommerce.model.persistence.repositories;

import com.jose.ecommerce.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jose.ecommerce.model.persistence.Cart;

interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user)
}
