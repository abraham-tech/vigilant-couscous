package edu.miu.springboottest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Place an order for a product.
     *
     * @param productId Product to order.
     * @param quantity  Quantity to order.
     * @return The created Order object.
     */
    public Order placeOrder(Long productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOpt.get();

        if (product.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock available");
        }

        // Deduct stock
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        // Create order
        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);

        return orderRepository.save(order);
    }

    /**
     * Cancel an order and restock the product.
     *
     * @param orderId ID of the order to cancel.
     */
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }

        Order order = orderOpt.get();
        Product product = order.getProduct();

        // Restock the product
        product.setStock(product.getStock() + order.getQuantity());
        productRepository.save(product);

        // Delete the order
        orderRepository.delete(order);
    }
}

