package com.jose.ecommerce.controllers

import groovy.util.logging.Slf4j

import com.jose.ecommerce.model.persistence.User
import com.jose.ecommerce.model.persistence.UserOrder
import com.jose.ecommerce.model.persistence.repositories.OrderRepository
import com.jose.ecommerce.model.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
@Slf4j
class OrderController {
	
	
	@Autowired
	private final UserRepository userRepository
	
	@Autowired
	private final OrderRepository orderRepository

	OrderController(UserRepository userRepository, OrderRepository orderRepository) {
		this.userRepository = userRepository
		this.orderRepository = orderRepository
	}

	@PostMapping("/submit/{username}")
	ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username)
		if(user == null) {
			log.info("User Not Found")
			return ResponseEntity.notFound().build()
		}
		UserOrder order = UserOrder.createFromCart(user.getCart())
		orderRepository.save(order)
		return ResponseEntity.ok(order)
	}
	
	@GetMapping("/history/{username}")
	ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username)
		if(user == null) {
			log.info("No Orders Found")
			return ResponseEntity.notFound().build()
		}
		return ResponseEntity.ok(orderRepository.findByUser(user))
	}
}
