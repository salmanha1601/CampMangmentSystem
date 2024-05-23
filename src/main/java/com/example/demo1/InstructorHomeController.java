package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class InstructorHomeController implements Initializable {
    public ImageView image;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public Button home_btn;
    public Label label_btx;
    public ListView camper_lst;
    public Button activity_btn;
    public ListView activity_lst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBUtils.setUser_id("2222");
        try {
            fillCampers();
            fillActivites();
            changeWelcomeLabel(DBUtils.getUser_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        home_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signIn.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        activity_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addActivity.fxml")));
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

    public void changeWelcomeLabel(String userId) throws SQLException {
        String sql="select * from users where userId='"+userId+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        rs.next();
        label_btx.setText("Welcome "+rs.getString("name"));
    }

    public void fillCampers() throws SQLException {
        String getGroup_qry="select * from instructors where id = '"+DBUtils.getUser_id()+"';";
        ResultSet rs=DBUtils.getFromDB(getGroup_qry);
        assert rs != null;
        rs.next();
        String group_id=rs.getString("group_id");
        System.out.println(group_id);
        String qry="select * from campers where group_id='"+group_id+"';";
        ResultSet resultSet=DBUtils.getFromDB(qry);
        while (resultSet!=null && resultSet.next()){
            camper_lst.getItems().add("Camper Name: "+resultSet.getString(3)+", And his number is: "+resultSet.getString(4));
        }
    }

    public void fillActivites() throws SQLException {
        String getGroup_qry="select * from instructors where id = '"+DBUtils.getUser_id()+"';";
        ResultSet rs=DBUtils.getFromDB(getGroup_qry);
        assert rs != null;
        rs.next();
        String group_id=rs.getString("group_id");
        System.out.println(group_id);
        String qry="select * from activities where group_id='"+group_id+"' and instructor_id='"+DBUtils.getUser_id()+"';";
        ResultSet resultSet=DBUtils.getFromDB(qry);
        while (resultSet!=null && resultSet.next()){
            activity_lst.getItems().add("Activity Description: "+resultSet.getString(5)+", And The Date: "+resultSet.getString(4));
        }
    }
}
