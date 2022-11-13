package org.itech.redolf.controller;

import lombok.RequiredArgsConstructor;
import org.itech.redolf.dto.InventoryRequest;
import org.itech.redolf.dto.InventoryResponse;
import org.itech.redolf.service.InventoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    private List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    private void createInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.createInventory(inventoryRequest);
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    private List<InventoryResponse> getAllInventory(){
        return inventoryService.getAllInventory();
    }
}
