package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReceiptsController implements Initializable {
    public ListView list;
    public Button Home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public Button pay_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillReceiptsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        pay_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("payment.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        Home_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("parentHomePage.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    public void fillReceiptsList() throws SQLException {
        String qry="select * from payments where user_id='"+DBUtils.getUser_id()+"';";
        ResultSet resultSet=DBUtils.getFromDB(qry);
        while (resultSet!=null && resultSet.next()){
            list.getItems().add("You'r Payment ID: "+resultSet.getString(3)+", And the amount is: "+resultSet.getString(1));
        }
    }
}
