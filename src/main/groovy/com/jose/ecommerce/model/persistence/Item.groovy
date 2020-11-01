package com.jose.ecommerce.model.persistence

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

import com.fasterxml.jackson.annotation.JsonProperty

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	Long id
	
	@Column(nullable = false)
	@JsonProperty
	String name
	
	@Column(nullable = false)
	@JsonProperty
	BigDecimal price
	
	@Column(nullable = false)
	@JsonProperty
	String description
	
	@Override
	int hashCode() {
		final int prime = 31
		int result = 1
		result = prime * result + ((id == null) ? 0 : id.hashCode())
		return result
	}

	@Override
	boolean equals(Object obj) {
		if (this == obj)
			return true
		if (obj == null)
			return false
		if (getClass() != obj.getClass())
			return false
		Item other = (Item) obj
		if (id == null) {
			return other.id == null
		} else return id == other.id
	}

}
