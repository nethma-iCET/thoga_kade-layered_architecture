package service.impl;

import model.dto.Customer;
import model.dto.Item;
import service.CustomerService;
import service.ItemService;
import service.PlaceOrderService;

public class PlaceOrderServiceImpl implements PlaceOrderService {

    ItemService itemService = new ItemServiceImpl();
    CustomerService customerService = new CustomerServiceImpl();

    @Override
    public Customer getCustomer(String customerId) {
        return customerService.getCustomer(customerId);
    }

    @Override
    public Item getItem(String itemCode) {
        return itemService.searchItem(itemCode, null);
    }
}
