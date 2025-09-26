package controller.itemController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dto.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService{
    @Override
    public ObservableList<Item> getAll() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM item";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
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
        String SQL = "INSERT INTO item Values(? ,? ,? ,? ,?)";


        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,newItem.getItemCode());
            psTm.setObject(2,newItem.getDescription());
            psTm.setObject(3,newItem.getPackSize());
            psTm.setObject(4,newItem.getUnitPrice());
            psTm.setObject(5,newItem.getQtyOnHand());

            psTm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItem(String itemCode) {
        String Sql = "DELETE FROM item WHERE ItemCode = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTM = connection.prepareStatement(Sql);
            psTM.setObject(1,itemCode);
            psTM.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Item updateItem) {
        String SQL = "UPDATE item SET Description = ? ,PackSize = ? ,UnitPrice = ? ,QtyOnHand = ? WHERE ItemCode =?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(5,updateItem.getItemCode());
            psTm.setObject(1,updateItem.getDescription());
            psTm.setObject(2,updateItem.getPackSize());
            psTm.setObject(3,updateItem.getUnitPrice());
            psTm.setObject(4,updateItem.getQtyOnHand());
            psTm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String itemCode, String description) {
        String SQL = "SELECT * FROM item WHERE ItemCode = ? OR Description= ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
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
