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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameController implements Initializable {

    @FXML
    private StackPane stackPane_main;
    @FXML
    private Button button_play;
    @FXML
    private Button button_setting;
    @FXML
    private Button button_exit;
    @FXML
    private Text text_tittle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // CARGAR RECURSOS
        Font tittleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/abduction2002.ttf"), 80);
        // Reproductor.playMusic(1);

        // ASIGNAR ACCIONES Y ESTILO
        text_tittle.getStyleClass().add("tittle");
        text_tittle.setFont(tittleFont);
        button_exit.getStyleClass().add("buttonStartScene");
        button_play.getStyleClass().add("buttonStartScene");
        button_setting.getStyleClass().add("buttonStartScene");

        button_exit.setOnAction(evnt -> exit());
        button_play.setOnAction(evnt -> play());
    }

    private void play() {
        try {
            App.setRoot("selectCharacter");
        } catch (IOException e) {
            System.err.println("ERROR AL CARGAR FXML");
            e.printStackTrace();
        }
    }

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
