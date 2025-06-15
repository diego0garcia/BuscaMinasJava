package es.diego.buscaminas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import javafx.scene.text.Text;

public class FinalScreenController implements Initializable {

         @FXML
         private Text text_tittle;
         @FXML
         private Text text_dates;
         @FXML
         private Button button_restart;
         @FXML
         private Button button_exit;

         @Override
         public void initialize(URL url, ResourceBundle rb) {
                  if (BoardController.isWin()) {
                           text_tittle.setText("VICTORIA");
                           text_dates.setText("Enhorabuena " + SelectCharacterController.getPlayer().getName() + " tienes ya un total de victorias de: " + SelectCharacterController.getPlayer().getWins());
                  } else {
                           text_tittle.setText("DERROTA");
                           text_dates.setText("Vaya...  " + SelectCharacterController.getPlayer().getName() + " tienes ya un total de derrotas de: " + SelectCharacterController.getPlayer().getDefeats());

                  }

                  button_restart.setOnAction(evnt -> nextAction());
                  button_exit.setOnAction(evnt -> exit());
         }

         //SIGUIENETE PANTALLA
         private void nextAction() {
                  try {
                           App.setRoot("selectCharacter");
                  } catch (IOException e) {
                           System.err.println("ERROR AL CARGAR FXML SelectCharacterController-next");
                  }
         }
         
         //SALIR DE LA APP
         private void exit() {
                  Alert exitAlert = new Alert(Alert.AlertType.INFORMATION);
                  exitAlert.setTitle("SALIR");
                  exitAlert.setHeaderText("¿Seguro que quieres salir?");
                  exitAlert.setContentText("Si sales, saldras de la aplicaión. ¿De verdad quieres eso?");

                  ButtonType accept = new ButtonType("Aceptar");
                  ButtonType decline = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                  exitAlert.getButtonTypes().setAll(accept, decline);

                  Optional<ButtonType> result = exitAlert.showAndWait();

                  if (result.get() == accept) {
                           App.close();
                  }
         }

}
