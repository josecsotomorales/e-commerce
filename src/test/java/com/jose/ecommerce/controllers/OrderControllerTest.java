package com.jose.ecommerce.controllers;

import com.jose.ecommerce.model.persistence.Cart;
import com.jose.ecommerce.model.persistence.Item;
import com.jose.ecommerce.model.persistence.User;
import com.jose.ecommerce.model.persistence.UserOrder;
import com.jose.ecommerce.model.persistence.repositories.OrderRepository;
import com.jose.ecommerce.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void init() {
        orderController = new OrderController(userRepository, orderRepository);
    }

    @Test
    public void placeOrderTest() {

        Item item = new Item();
        item.setId(1L);
        item.setName("theItem");
        item.setDescription("theDescription");
        item.setPrice(BigDecimal.valueOf(10));

        User user = new User();
        user.setUsername("josesoto");
        Cart cart = new Cart();
        cart.addItem(item);
        user.setCart(cart);

        when(userRepository.findByUsername("josesoto")).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit("josesoto");
        UserOrder userOrder = response.getBody();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf(10),item.getPrice());

    }

    @Test
    public void findOrderByUser() {

        User user = new User();
        user.setUsername("josesoto");

        Cart cart = new Cart();
        cart.setUser(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("theItem1");
        item.setDescription("theDescription1");
        item.setPrice(BigDecimal.valueOf(10));

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("theItem2");
        item2.setDescription("theDescription2");
        item2.setPrice(BigDecimal.valueOf(20));

        cart.addItem(item);
        cart.addItem(item2);
        user.setCart(cart);

        UserOrder userOrder = UserOrder.createFromCart(cart);
        List<UserOrder> usersOrders = new ArrayList<>();
        usersOrders.add(userOrder);

        when(userRepository.findByUsername("josesoto")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(usersOrders);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("josesoto");
        List<UserOrder> returnedOrders = response.getBody();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assert returnedOrders != null;
        assertEquals(BigDecimal.valueOf(30),returnedOrders.get(0).getTotal());

    }

}
