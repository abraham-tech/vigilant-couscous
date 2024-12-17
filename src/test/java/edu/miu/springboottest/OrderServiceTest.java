package edu.miu.springboottest;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder_success() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.00);
        product.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order order = orderService.placeOrder(1L, 2);

        // Assert
        assertNotNull(order);
        assertEquals(2, order.getQuantity());
        assertEquals(2000.00, order.getTotalPrice());
        assertEquals(8, product.getStock()); // Stock should decrease

        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void placeOrder_insufficientStock() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.00);
        product.setStock(1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            orderService.placeOrder(1L, 2);
        });

        assertEquals("Insufficient stock available", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void cancelOrder_success() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.00);
        product.setStock(8);

        Order order = new Order();
        order.setId(1L);
        order.setProduct(product);
        order.setQuantity(2);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        orderService.cancelOrder(1L);

        // Assert
        assertEquals(10, product.getStock()); // Stock should be restored
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void cancelOrder_notFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.cancelOrder(1L);
        });

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, never()).delete(any());
    }
}

