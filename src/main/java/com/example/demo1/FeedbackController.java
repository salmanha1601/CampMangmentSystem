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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FeedbackController implements Initializable {
    public Button home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextArea feedback_txt;
    public Button submit_btn;
    public TextField campId_txt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home_btn.setOnAction(new EventHandler<ActionEvent>() {
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

        submit_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if (feedback_txt.getText().isEmpty() || campId_txt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill all the fields");
                    alert.show();
                }
                else{
                    String qry= "INSERT INTO feedback (user_id, camp_id , feedback_content) VALUES ("+DBUtils.getUser_id()+", '"+campId_txt.getText()+"', '"+feedback_txt.getText()+"');";
                    DBUtils.addToDB(qry);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Thank you for your feedback!");
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
            }
        });
    }
}
