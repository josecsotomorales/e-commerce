package com.jose.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty

class ModifyCartRequest {
	
	@JsonProperty
	String username
	
	@JsonProperty
	long itemId
	
	@JsonProperty
	int quantity

}
