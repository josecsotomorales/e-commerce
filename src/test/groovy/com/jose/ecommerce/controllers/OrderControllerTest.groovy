package com.jose.ecommerce.controllers

import com.jose.ecommerce.model.persistence.Cart
import com.jose.ecommerce.model.persistence.Item
import com.jose.ecommerce.model.persistence.User
import com.jose.ecommerce.model.persistence.UserOrder
import com.jose.ecommerce.model.persistence.repositories.OrderRepository
import com.jose.ecommerce.model.persistence.repositories.UserRepository
import org.junit.Before
import org.junit.Test
import org.springframework.http.ResponseEntity

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class OrderControllerTest {

    private OrderController orderController
    private final UserRepository userRepository = mock(UserRepository.class)
    private final OrderRepository orderRepository = mock(OrderRepository.class)

    @Before
    void init() {
        orderController = new OrderController(userRepository, orderRepository)
    }

    @Test
    void placeOrderTest() {

        def item = new Item()
        item.id = 1L
        item.name = "theItem"
        item.description = "theDescription"
        item.price = 10L

        def user = new User()
        user.id = 1L
        user.username = "jose"
        def cartObj = new Cart()
        user.cart = cartObj

        when(userRepository.findByUsername("jose")).thenReturn(user)

        ResponseEntity<UserOrder> response = orderController.submit("jose")
        UserOrder userOrder = response.getBody()
        assertNotNull(response)
        assertEquals(200, response.getStatusCodeValue())
        assertEquals(10L, item.getPrice())

    }

    @Test
    void findOrderByUser() {

        def user = new User()
        user.username = "jose"

        def cart = new Cart()
        cart.user = user

        def item = new Item()
        item.id = 1L
        item.name = "theItem1"
        item.description = "theDescription1"
        item.price = 10L

        def item2 = new Item()
        item2.id = 2L
        item2.name = "theItem2"
        item2.description = "theDescription2"
        item2.price = 20L

        cart.addItem(item)
        cart.addItem(item2)
        user.cart = cart

        def userOrder = UserOrder.createFromCart(cart)
        List<UserOrder> usersOrders = new ArrayList<>()
        usersOrders.add(userOrder)

        when(userRepository.findByUsername("jose")).thenReturn(user)
        when(orderRepository.findByUser(user)).thenReturn(usersOrders)

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("jose")
        List<UserOrder> returnedOrders = response.getBody()
        assertNotNull(response)
        assertEquals(200, response.getStatusCodeValue())
        assert returnedOrders != null
        assertEquals(30L, returnedOrders.get(0).getTotal())

    }

}
