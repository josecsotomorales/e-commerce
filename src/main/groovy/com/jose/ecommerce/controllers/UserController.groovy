package com.jose.ecommerce.controllers

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.jose.ecommerce.model.persistence.Cart
import com.jose.ecommerce.model.persistence.User
import com.jose.ecommerce.model.persistence.repositories.CartRepository
import com.jose.ecommerce.model.persistence.repositories.UserRepository
import com.jose.ecommerce.model.requests.CreateUserRequest

@RestController
@RequestMapping("/api/user")
@Slf4j
class UserController {
	
	@Autowired
	private final UserRepository userRepository
	
	@Autowired
	private final CartRepository cartRepository

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder

	UserController(UserRepository userRepository, CartRepository cartRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository
		this.cartRepository = cartRepository
		this.bCryptPasswordEncoder = bCryptPasswordEncoder
	}

	@GetMapping("/id/{id}")
	ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id))
	}
	
	@GetMapping("/{username}")
	ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username)
		if(user == null) {
			log.info("User Not Found")
			return ResponseEntity.notFound().build()
		} else {
			return ResponseEntity.ok(user)
		}
	}
	
	@PostMapping("/create")
	ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User()
		user.setUsername(createUserRequest.getUsername())
		Cart cart = new Cart()
		cartRepository.save(cart)
		user.setCart(cart)
		if(createUserRequest.getPassword().length() < 6 ||
				!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			log.error("Error with user password. Cannot create user ${createUserRequest.username}")
			return ResponseEntity.badRequest().build()
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()))
		userRepository.save(user)
		log.info("Created User: ${user.username}")
		return ResponseEntity.ok(user)
	}
	
}
