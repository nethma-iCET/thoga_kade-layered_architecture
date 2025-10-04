package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dto.Item;
import repository.ItemRepository;
import repository.impl.ItemRepositoryImpl;
import service.ItemService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemServiceImpl implements ItemService {
    
    ItemRepository itemRepository = new ItemRepositoryImpl();
    
    @Override
    public ObservableList<Item> getAll() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = itemRepository.getAll();
            while (resultSet.next()) {
                itemObservableList.add(new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemObservableList;
    }

    @Override
    public void addItem(Item newItem) {

        try {
            itemRepository.addItem(newItem);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItem(String itemCode) {
        Connection connection = null;
        try {
            itemRepository.deleteItem(itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Item updateItem) {
        try {
            itemRepository.updateItem(updateItem);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String itemCode, String description) {
        try {
            ResultSet resultSet = itemRepository.searchItem(itemCode, description);
            resultSet.next();
            return new Item(
                    resultSet.getString("ItemCode"),
                    resultSet.getString("Description"),
                    resultSet.getString("PackSize"),
                    resultSet.getDouble("UnitPrice"),
                    resultSet.getInt("QtyOnHand")
            );
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This ItemCode not in DataBase");
            alert.show();
            throw new RuntimeException(e);
        }
    }
}
