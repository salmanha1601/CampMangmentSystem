package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

public class AdminHomeController implements Initializable {
    public ImageView image;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public javafx.scene.control.Label Label;
    public ListView list;
    public ChoiceBox drop_menu;
    public Button home_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBUtils.setUser_id("2222");
        try {
            changeWelcomeLabel(DBUtils.getUser_id());
            fillGroups();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Add items to drop menu
        String[] menu_items={"Generate Group","Sign Instructor","logOut"};
        drop_menu.getItems().addAll(menu_items);
        drop_menu.setOnAction(this::changeScreens);
    }

    public void changeScreens(Event event) {
        String choice= drop_menu.getValue().toString();

        Parent root = null;

        if(choice.equals("Generate Group")){
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GenerateGroup.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(choice.equals("Sign Instructor")){
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInstructor.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(choice.equals("logOut")){
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

    public void fillGroups() throws SQLException {
        String getCamp_qry="select * from camps where owner_id = '"+DBUtils.getUser_id()+"';";
        ResultSet rs=DBUtils.getFromDB(getCamp_qry);
        assert rs != null;
        rs.next();
        String camp_id=rs.getString("camp_id");
        System.out.println(camp_id);
        String qry="select * from camp.groups where camp_id='"+camp_id+"';";
        ResultSet resultSet=DBUtils.getFromDB(qry);
        while (resultSet!=null && resultSet.next()){
            list.getItems().add("Camper Name: "+resultSet.getString(3)+", And his number is: "+resultSet.getString(4));
        }
    }

    public void changeWelcomeLabel(String userId) throws SQLException {
        String sql="select * from users where userId='"+userId+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        rs.next();
        Label.setText("Welcome "+rs.getString("name"));
    }
}
