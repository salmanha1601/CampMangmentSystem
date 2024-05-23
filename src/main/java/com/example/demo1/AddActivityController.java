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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class AddActivityController implements Initializable {
    public Button home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public DatePicker date;
    public Button createActivity_btn;
    public TextField activity_txt;
    public Button Home_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Home_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InstructorHome.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        createActivity_btn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.setUser_id("2222");
                String group_id="";
                try {
                    String qry="select * from instructors where id='"+DBUtils.getUser_id()+"';";
                    ResultSet resultSet=DBUtils.getFromDB(qry);
                    resultSet.next();
                    group_id=resultSet.getString("group_id");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String qry= "insert into activities (instructor_id,group_id,date,description) values('"+DBUtils.getUser_id()+"','"+group_id+"','"+date.getValue().toString()+"','"+activity_txt.getText()+"');";
                DBUtils.addToDB(qry);
                Alert  alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Successfully added activity");
                alert.show();
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InstructorHome.fxml")));
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

