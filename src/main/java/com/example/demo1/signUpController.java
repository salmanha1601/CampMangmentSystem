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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class signUpController implements Initializable {
    public Button Home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField name_txt;
    public TextField ID_txt;
    public TextField phone_txt;
    public PasswordField pass_txt;
    public TextField email_txt;
    public Button signUp_bttn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Home_btn.setOnAction(new EventHandler<ActionEvent>() {
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

        signUp_bttn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                if(ID_txt.getText().isEmpty() || phone_txt.getText().isEmpty() || pass_txt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill all the fields");
                    alert.show();
                }
                else{
                    String qry= "INSERT INTO users VALUES('"+ID_txt.getText()+"','"+pass_txt.getText()+"','"+name_txt.getText()+"','"+phone_txt.getText()+"','"+email_txt.getText()+"')";
                    DBUtils.addToDB(qry);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Congrats You're Signed");
                    alert.show();
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
            }
        });
    }
}
