package service;

import model.dto.Customer;
import model.dto.Item;

public interface PlaceOrderService {
    Customer getCustomer(String customerId);

    Item getItem(String itemCode);
}
