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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.views.DocumentView;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignInstructorController implements Initializable {
    public ImageView image;
    public Button home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField name_txt;
    public TextField ID_txt;
    public PasswordField password;
    public Button sign_btn;
    public TextField bDetails_txt;
    public TextField Gid_txt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        sign_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //add instructor to the instructors dataset
                String insert_qry="Insert into instructors values('"+ID_txt.getText()+"','"+name_txt.getText()+"','"+password.getText()+"','"+Gid_txt.getText()+"','"+bDetails_txt.getText()+"');";
                if(check_details()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Decline");
                    alert.setContentText("Details already exist");
                    alert.show();
                }
                else{
                    DBUtils.addToDB(insert_qry);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("The information is inserted successfully");
                    alert.show();
                }

                //add instructor if dosn't exist
                String insert_to_uers_qry= "Insert into users values ('"+ID_txt.getText()+"','"+password.getText()+"','"+name_txt.getText()+"','"+"0504443934"+"','"+name_txt.getText()+"@gmail.com"+"');";
                DBUtils.addToDB(insert_to_uers_qry);

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

            private boolean check_details() {
                String check_qry="Select * from instructors where id='"+ID_txt.getText()+"' and '"+Gid_txt.getText()+"';";
                ResultSet resultSet= DBUtils.getFromDB(check_qry);
                if(resultSet==null)
                    return false;
                return true;
            }

        });
    }
}
