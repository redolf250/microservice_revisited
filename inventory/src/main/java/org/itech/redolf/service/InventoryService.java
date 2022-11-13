package org.itech.redolf.service;

import lombok.RequiredArgsConstructor;
import org.itech.redolf.dto.InventoryRequest;
import org.itech.redolf.dto.InventoryResponse;
import org.itech.redolf.model.Inventory;
import org.itech.redolf.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    public  List<InventoryResponse> isInStock(List<String> sku_code){
        return repository.findBySkuCodeIn(sku_code)
                .stream().map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
                ).toList();
    }

    public void createInventory(InventoryRequest inventoryRequest){
        Inventory inventory = mapper.map(inventoryRequest,Inventory.class);
        repository.save(inventory);
    }

    public List<InventoryResponse> getAllInventory(){
        List<Inventory> inventory = repository.findAll();
        return inventory.stream().map(this::data).toList();
    }

    private InventoryResponse data(Inventory inventory){
       return  mapper.map(inventory,InventoryResponse.class);
    }
}
