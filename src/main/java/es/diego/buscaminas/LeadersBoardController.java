package es.diego.buscaminas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LeadersBoardController implements Initializable {

         @FXML
         private Text text_p2;
         @FXML
         private Text text_p1;
         @FXML
         private Text text_p3;
         @FXML
         private Button button_back;
         @FXML
         private Text text_d1;
         @FXML
         private Text text_d2;
         @FXML
         private Text text_d3;

         @Override
         public void initialize(URL url, ResourceBundle rb) {
                  if ((!GameController.getMisDatos().isEmpty())) {
                           text_p1.setText(GameController.getMisDatos().get(0).getName());
                           text_d1.setText("Victorias: " +GameController.getMisDatos().get(0).getWins());
                  } else {
                           text_p1.setText("");
                           text_d1.setText("");
                  }
                  if ((GameController.getMisDatos().size() > 1)) {
                           text_p2.setText(GameController.getMisDatos().get(1).getName());
                           text_d2.setText("Victorias: " +GameController.getMisDatos().get(1).getWins());
                  } else {
                           text_p2.setText("");
                           text_d2.setText("");
                  }
                  if ((GameController.getMisDatos().size() > 2)) {
                           text_p3.setText(GameController.getMisDatos().get(2).getName());
                           text_d3.setText("Victorias: " +GameController.getMisDatos().get(2).getWins());
                  } else {
                           text_p3.setText("");
                           text_d3.setText("");
                  }

                  button_back.setOnAction(evnt -> {
                           Node n = (Node) evnt.getSource();
                           n.getScene().getWindow().hide();
                  });
         }

}
