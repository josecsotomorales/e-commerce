package com.jose.ecommerce.model.persistence

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

import com.fasterxml.jackson.annotation.JsonProperty

@Entity
@Table(name = "user_order")
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	Long id
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonProperty
	@Column
    List<Item> items
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
	@JsonProperty
    User user
	
	@JsonProperty
	@Column
	BigDecimal total

	static UserOrder createFromCart(Cart cart) {
		UserOrder order = new UserOrder()
		order.items = new ArrayList<>(cart.items)
		order.total = cart.total
		order.user = cart.user
		return order
	}
	
}
