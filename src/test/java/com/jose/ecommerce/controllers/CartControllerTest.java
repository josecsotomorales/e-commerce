package com.jose.ecommerce.controllers;

import com.jose.ecommerce.model.persistence.Cart;
import com.jose.ecommerce.model.persistence.Item;
import com.jose.ecommerce.model.persistence.User;
import com.jose.ecommerce.model.persistence.repositories.CartRepository;
import com.jose.ecommerce.model.persistence.repositories.ItemRepository;
import com.jose.ecommerce.model.persistence.repositories.UserRepository;
import com.jose.ecommerce.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void init() {
        cartController = new CartController(userRepository, cartRepository, itemRepository);
    }

    @Test
    public void addToCartSuccess() {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("josesoto");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(2);

        User user = new User();
        user.setId(1L);
        user.setUsername("josesoto");
        Cart cart = new Cart();
        user.setCart(cart);

        Item item = new Item();
        item.setId(1L);
        item.setName("theItem");
        item.setDescription("theDescription");
        item.setPrice(BigDecimal.valueOf(15));

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        cart = response.getBody();
        assertEquals(200,response.getStatusCodeValue());
        assert cart != null;
        assertEquals("theItem",cart.getItems().get(0).getName());
        assertEquals(BigDecimal.valueOf(30),cart.getTotal());
    }

    @Test
    public void removeFromCart() {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("josesoto");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);

        User user = new User();
        user.setId(1L);
        user.setUsername("josesoto");
        Cart cartObj = new Cart();
        user.setCart(cartObj);

        Item item = new Item();
        item.setId(1L);
        item.setName("theItem");
        item.setDescription("theDescription");
        item.setPrice(BigDecimal.valueOf(15));


        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        Cart cart = response.getBody();
        assertEquals(200,response.getStatusCodeValue());
    }

}
