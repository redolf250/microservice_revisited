package org.itech.redolf.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.itech.redolf.dto.OrderLineItemsResponse;
import org.itech.redolf.dto.OrderRequest;
import org.itech.redolf.dto.OrderResponse;
import org.itech.redolf.model.OrderLineItems;
import org.itech.redolf.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    private void placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
    }



    @GetMapping("/list/orders")
    @ResponseStatus(HttpStatus.OK)
    private List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }
}
