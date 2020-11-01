package com.jose.ecommerce.security


import com.jose.ecommerce.model.persistence.User
import com.jose.ecommerce.model.persistence.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import static java.util.Collections.emptyList

@Service
class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository

    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User applicationUser = userRepository.findByUsername(username)
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username)
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList())
    }
}
