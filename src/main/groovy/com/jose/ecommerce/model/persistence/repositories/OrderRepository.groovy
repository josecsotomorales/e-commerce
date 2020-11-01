package com.jose.ecommerce.model.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jose.ecommerce.model.persistence.User;
import com.jose.ecommerce.model.persistence.UserOrder;

interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user)
}
