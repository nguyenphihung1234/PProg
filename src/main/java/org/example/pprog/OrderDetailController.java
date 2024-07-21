package org.example.pprog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailController {
    private  final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailController(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return ResponseEntity.ok(savedOrderDetail);
    }

    @GetMapping
    public  ResponseEntity<List<OrderDetail>> getAllOrderDetails(){
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return ResponseEntity.ok(orderDetails);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Integer id){
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with " + id));
        return ResponseEntity.ok(orderDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetail orderDetailDetails ){
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with id " + id));

        orderDetail.setOrder(orderDetailDetails.getOrder());
        orderDetail.setProduct(orderDetailDetails.getProduct());
        orderDetail.setQuantity(orderDetailDetails.getQuantity());
        orderDetail.setPrice(orderDetailDetails.getPrice());

        OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
        return ResponseEntity.ok(updatedOrderDetail);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with id " + id));

        orderDetailRepository.delete(orderDetail);
        return ResponseEntity.noContent().build();
    }
}
