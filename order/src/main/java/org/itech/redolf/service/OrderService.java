package org.itech.redolf.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.itech.redolf.dto.*;
import org.itech.redolf.model.Order;
import org.itech.redolf.model.OrderLineItems;
import org.itech.redolf.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService{
    private final OrderRepository repository;
    private final WebClient webClient;
    private final ModelMapper modelmapper;
    
    @CircuitBreaker(name = "inventory-service")
    public void placeOrder(OrderRequest orderRequest){
        Order order = modelmapper.map(orderRequest, Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> items= orderRequest.getOrderLineItemsRequests()
                .stream()
                .map(this::orderLineItems)
                .toList();
        order.setOrderLineItems(items);

        List<String> skuCodes=order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponse = webClient.get()
                .uri("/",
                        uriBuilder -> uriBuilder.
                                queryParam("skuCode",skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        assert inventoryResponse != null;
        boolean result=Arrays.stream(inventoryResponse).allMatch(InventoryResponse::isInStock);
        if (result){
            repository.save(order);
        }else {
            throw new IllegalStateException("Product out of stock");
        }

    }
    public List<OrderResponse> getAllOrders() {
     List<Order> order = repository.findAll();
     return order.stream().map(this::data).toList();
    }
    private OrderResponse data(Order order){
       return modelmapper.map(order,OrderResponse.class);
    }
    private OrderLineItems orderLineItems(OrderLineItemsRequest request){
        return modelmapper.map(request,OrderLineItems.class);
    }
}
