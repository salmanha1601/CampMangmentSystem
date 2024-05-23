package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class signInController implements Initializable {
    public ImageView image;
    public CheckBox admin_checkbox;
    public CheckBox instructor_checkbox;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField id_txtf;
    @FXML
    private PasswordField pass_txtf;
    @FXML
    private Button signin_bttn;
    @FXML
    private Button signup_bttn;

    public boolean checkUsername(ActionEvent event){
        String id = id_txtf.getText();
        String pass = pass_txtf.getText();
        String sql="select * from users where userId='"+id+"' and password='"+pass+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        if(rs==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("User not found");
            alert.show();
            return false;
        }else{
            return true;
        }
    }

    public boolean checkAdmin(ActionEvent event){
        String id = id_txtf.getText();
        String pass = pass_txtf.getText();
        String sql="select * from camps where owner_id='"+id+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        if(rs==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You're not Admin");
            alert.show();
            return false;
        }else{
            return true;
        }
    }

    private boolean checkInstructor(ActionEvent actionEvent) {
        String id = id_txtf.getText();
        String sql="select * from instructors where id='"+id+"';";
        ResultSet rs=DBUtils.getFromDB(sql);
        if(rs==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You're not instructor");
            alert.show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            signin_bttn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(checkUsername(actionEvent)){
                        String id = id_txtf.getText();
                        DBUtils.setUser_id(id);
                        if(admin_checkbox.isSelected() && checkAdmin(actionEvent)){
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }else{
                            if(instructor_checkbox.isSelected() && checkInstructor(actionEvent)){
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("InstructorHome.fxml"));
                                    stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }else {
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("parentHomePage.fxml"));
                                    stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                }
            });

            signup_bttn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signUp.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            });
    }

}
