package com.jose.ecommerce.model.persistence

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table

import com.fasterxml.jackson.annotation.JsonProperty

@Entity
@Table(name = "cart")
class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	Long id
	
	@ManyToMany
	@JsonProperty
	@Column
	List<Item> items
	
	@OneToOne(mappedBy = "cart")
	@JsonProperty
    User user
	
	@Column
	@JsonProperty
	BigDecimal total
	
	void addItem(Item item) {
		if(items == null) {
			items = new ArrayList<>()
		}
		items.add(item)
		if(total == null) {
			total = new BigDecimal(0)
		}
		total = total.add(item.price)
	}
	
	void removeItem(Item item) {
		if(!items) {
			items = new ArrayList<>()
		}
		items.remove(item)
		if(!total) {
			total = new BigDecimal(0)
		}
		total = total.subtract(item.price)
	}
}
