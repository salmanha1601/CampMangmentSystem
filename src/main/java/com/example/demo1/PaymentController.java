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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public Button home_btn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField amount_txt;
    public TextField kidID_txt;
    public Button pay_btn;
    public ProgressIndicator load;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        home_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Receipts.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        pay_btn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
                double progres=0;
                load.setVisible(true);
                for (int i = 0; i <= 4; i++) {
                    progres+=20;
                    load.setProgress(progres);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                load.setVisible(false);

                if(Integer.parseInt(amount_txt.getText())<=0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Amount Error");
                    alert.setHeaderText("Amount Error");
                    alert.setContentText("The amount should be greater than 0");
                    alert.show();
                }else{
                    String qry= "INSERT INTO payments (amount, camp_id , user_id) VALUES ("+Integer.parseInt(amount_txt.getText())+", '"+kidID_txt.getText()+"', '"+DBUtils.getUser_id()+"');";
                    DBUtils.addToDB(qry);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Payment Successful");
                    alert.show();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Receipts.fxml")));
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
