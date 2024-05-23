package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class parentHomePageController implements Initializable {
    public Label Change_label;
    public ListView listV;
    public ChoiceBox choiceBox;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            changeWelcomeLabel(DBUtils.getUser_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            load_list(DBUtils.getUser_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Add items to drop menu
        String[] menu_items={"Sign A Kid","Receipts","Feedback","logOut"};
        choiceBox.getItems().addAll(menu_items);
        choiceBox.setOnAction(this::changeScreens);
    }

    private void changeScreens(Event event) {
        String choice= choiceBox.getValue().toString();

        Parent root = null;

        if(choice.equals("Sign A Kid")){
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signKid.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(choice.equals("Receipts")){
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Receipts.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (choice.equals("Feedback")) {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Feedback.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(choice.equals("logOut")){
            DBUtils.setUser_id("");
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signIn.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeWelcomeLabel(String userId) throws SQLException {
        String sql="select * from users where userId='"+userId+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        rs.next();
        Change_label.setText("Welcome "+rs.getString("name"));
    }

    public void load_list(String userId) throws SQLException {
        String sql="select * from campers where parent_id='"+userId+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        while (rs!=null && rs.next()){
            listV.getItems().add( rs.getString("name")+"'s id: "+rs.getString("camper_id")+" and his camps id is: "+rs.getString("camp_id"));
        }
    }
}
