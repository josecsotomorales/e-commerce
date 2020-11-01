package com.jose.ecommerce.model.persistence.repositories;

import com.jose.ecommerce.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;

interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByName(String name)
}
