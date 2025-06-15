package es.diego.buscaminas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import es.diego.buscaminas.propias.Jugador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class AddPlayerController implements Initializable {

         @FXML
         private TextField textField_name;
         @FXML
         private Button button_decline;
         @FXML
         private Button button_accept;

         private boolean cancelar = true;
         private Jugador jgdr = null;

         @Override
         public void initialize(URL url, ResourceBundle rb) {
                  setCancelar(true);
                  button_accept.setOnAction(evnt -> accept(evnt));
                  button_decline.setOnAction(evnt -> close(evnt));
         }

         public void initPlayer(Jugador player) {
                  textField_name.setText(player.getName());
         }

         private void accept(ActionEvent evnt) {
                  // COMPROBAMOS QUE TODOS LOS CAMPOS ESTEN LLENOS
                  if (!textField_name.getText().trim().equals("")) {
                           if (validName(textField_name.getText()) == true) {
                                    setCancelar(false);
                                    setJugador(new Jugador(textField_name.getText().trim()));
                                    Node n = (Node) evnt.getSource();
                                    n.getScene().getWindow().hide();
                           } else {
                                    Alert exitAlert = new Alert(Alert.AlertType.INFORMATION);
                                    exitAlert.setTitle("NOMBRE REPETIO");
                                    exitAlert.setHeaderText("NOMBRE REPETIDO");
                                    exitAlert.setContentText("El nombre ya se encuentra en la lista");
                                    ButtonType decline = new ButtonType("Aceptar", ButtonBar.ButtonData.CANCEL_CLOSE);
                                    
                                    exitAlert.showAndWait();
                           }

                  } else {
                           // ERROR DE QUE LOS CAMPOS ESTAN VACIOS
                           Alert alerta = new Alert(Alert.AlertType.ERROR);
                           alerta.setTitle("Excepción");
                           alerta.setHeaderText("ERROR");
                           alerta.setContentText("Hay campos vacíos");
                           alerta.show();
                  }

         }

         private void close(ActionEvent evnt) {
                  setCancelar(true);
                  Node n = (Node) evnt.getSource();
                  n.getScene().getWindow().hide();
         }

         //COMPROBAR DUPLICADOS
         private boolean validName(String playerName) {
                  for (int i = 0; i < SelectCharacterController.getMisDatos().size(); i++) {
                           if (playerName.trim().equals(SelectCharacterController.getMisDatos().get(i).getName().trim())) {
                                    System.out.println("REPETIDO");
                                    return false;
                           }
                  }
                  System.out.println("NO REPETIDO");
                  return true;
         }

         // G&S
         public boolean getCancelar() {
                  return cancelar;
         }

         public void setCancelar(boolean cancelar) {
                  this.cancelar = cancelar;
         }

         public Jugador getJugador() {
                  return jgdr;
         }

         public void setJugador(Jugador persona) {
                  this.jgdr = persona;
         }

}
