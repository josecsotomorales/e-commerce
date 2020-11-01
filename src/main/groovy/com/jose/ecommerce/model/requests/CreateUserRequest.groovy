package com.jose.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty

class CreateUserRequest {

	@JsonProperty
	String username

	@JsonProperty
	String password

	@JsonProperty
	String confirmPassword

}
