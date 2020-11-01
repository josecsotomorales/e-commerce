package com.jose.ecommerce.controllers

import com.jose.ecommerce.model.persistence.Cart
import com.jose.ecommerce.model.persistence.Item
import com.jose.ecommerce.model.persistence.User
import com.jose.ecommerce.model.persistence.repositories.CartRepository
import com.jose.ecommerce.model.persistence.repositories.ItemRepository
import com.jose.ecommerce.model.persistence.repositories.UserRepository
import com.jose.ecommerce.model.requests.ModifyCartRequest
import org.junit.Before
import org.junit.Test
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class CartControllerTest {

    private CartController cartController
    private final UserRepository userRepository = mock(UserRepository.class)
    private final CartRepository cartRepository = mock(CartRepository.class)
    private final ItemRepository itemRepository = mock(ItemRepository.class)
    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class)

    @Before
    void init() {
        cartController = new CartController(userRepository, cartRepository, itemRepository)
    }

    @Test
    void addToCartSuccess() {

        def modifyCartRequest = new ModifyCartRequest()
        modifyCartRequest.username = "jose"
        modifyCartRequest.itemId =  1
        modifyCartRequest.quantity = 2

        def user = new User()
        user.id = 1L
        user.username = "jose"
        def cart = new Cart()
        user.cart = cart

        def item = new Item()
        item.id = 1L
        item.name = "theItem"
        item.description = "theDescription"
        item.price = 15L

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item))
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user)

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest)
        cart = response.getBody()
        assertEquals(200,response.getStatusCodeValue())
        assert cart != null
        assertEquals("theItem",cart.getItems().get(0).getName())
        assertEquals(BigDecimal.valueOf(30),cart.getTotal())
    }

    @Test
    void removeFromCart() {

        def modifyCartRequest = new ModifyCartRequest()
        modifyCartRequest.username = "jose"
        modifyCartRequest.itemId =  1
        modifyCartRequest.quantity = 2

        def user = new User()
        user.id = 1L
        user.username = "jose"
        def cartObj = new Cart()
        user.cart = cartObj

        def item = new Item()
        item.id = 1L
        item.name = "theItem"
        item.description = "theDescription"
        item.price = 15L


        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item))
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user)

        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest)
        Cart cart = response.getBody()
        assertEquals(200,response.getStatusCodeValue())
    }

}
