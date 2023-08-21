package com.fernandes.commerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandes.commerce.dto.OrderDTO;
import com.fernandes.commerce.dto.OrderItemDTO;
import com.fernandes.commerce.entities.Order;
import com.fernandes.commerce.entities.OrderItem;
import com.fernandes.commerce.entities.OrderStatus;
import com.fernandes.commerce.entities.Product;
import com.fernandes.commerce.entities.User;
import com.fernandes.commerce.repositories.OrderItemRepository;
import com.fernandes.commerce.repositories.OrderRepository;
import com.fernandes.commerce.repositories.ProductRepository;
import com.fernandes.commerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()){

            Product product = productRepository.getReferenceById(itemDTO.getProductId());

            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());

            order.getItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }

}
