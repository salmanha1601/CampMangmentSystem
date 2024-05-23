package com.example.demo1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class GenerateGroupController implements Initializable {
    public Button home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField name_txt;
    public TextField id_txt;
    public Slider min_age;
    public Slider max_age;
    public Label label_min;
    public Label label_max;
    public Button generate_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Adding Listener to value property.
        min_age.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        label_min.setText("min age: " + t1.intValue());
                    }
         });

        max_age.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        label_max.setText("max age: " + t1.intValue());
                    }
        });

        generate_btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.setUser_id("2222");
                String camp_qry="select * from camps where owner_id='"+DBUtils.getUser_id()+"';";
                ResultSet res=DBUtils.getFromDB(camp_qry);
                try {
                    res.next();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String camp_id= null;
                try {
                    camp_id = res.getString("camp_id");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println("INSERT INTO groups (group_id,group_name,min_age,max_age,camp_id) VALUES('"+id_txt.getText()+"','"+name_txt.getText()+"','"+(int)min_age.getValue()+"','"+(int)max_age.getValue()+"','"+camp_id+"');");
                String qry="INSERT INTO camp.groups (group_id,group_name,min_age,max_age,camp_id) VALUES('"+id_txt.getText()+"','"+name_txt.getText()+"','"+(int)min_age.getValue()+"','"+(int)max_age.getValue()+"','"+camp_id+"');";
                DBUtils.addToDB(qry);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Group Generated Successfully");
                alert.show();
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminHome.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        home_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminHome.fxml")));
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
