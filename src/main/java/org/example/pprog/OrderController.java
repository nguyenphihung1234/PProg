package org.example.pprog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final  OrderRepository orderRepository;

    @Autowired

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Order> getOrderById(@PathVariable Integer id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id" + id));
        return  ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order orderDetails ){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " +id));
        order.setCustomer(orderDetails.getCustomer());

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id" + id));

        orderRepository.delete(order);
        return  ResponseEntity.noContent().build();
    }


}
