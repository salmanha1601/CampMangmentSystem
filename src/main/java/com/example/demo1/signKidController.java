package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class signKidController implements Initializable {
    public TextField cod_txt;
    public Button Home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField name_txt;
    public TextField id_txt;
    public TextField phone_txt;
    public Button sign_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        sign_btn.setOnAction(new EventHandler<ActionEvent>(
        ){
            @Override
            public void handle(ActionEvent actionEvent) {
                String grps="SELECT group_id FROM camp.groups";
                ResultSet resultSet= DBUtils.getFromDB(grps);
                List<String> groupIds = new ArrayList<>();
                while (true) {
                    try {
                        assert resultSet != null;
                        if (!resultSet.next()) break;
                        groupIds.add(resultSet.getString("group_id"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                //Randomly select one group ID
                Random random = new Random();
                int randomIndex = random.nextInt(groupIds.size());
                String selectedGroupId = groupIds.get(randomIndex);

                //Insert camper in the table
                String qry= "INSERT INTO campers VALUES('"+id_txt.getText()+"','"+DBUtils.getUser_id()+"','"+name_txt.getText()+"','"+phone_txt.getText()+"','"+cod_txt.getText()+"','"+selectedGroupId+"');";
                DBUtils.addToDB(qry);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Congrats You're Signed");
                alert.show();
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
}
