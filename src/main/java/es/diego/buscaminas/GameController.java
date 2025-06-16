package es.diego.buscaminas;

import es.diego.buscaminas.propias.DataLoader;
import es.diego.buscaminas.propias.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController implements Initializable {

    @FXML
    private StackPane stackPane_main;
    @FXML
    private Button button_play;
    @FXML
    private Button button_exit;
    @FXML
    private Text text_tittle;
    @FXML
    private Button button_leaders;

    private static ArrayList<Jugador> misDatos;
    private static DataLoader dl = new DataLoader();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // CARGAR RECURSOS
        Font tittleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/abduction2002.ttf"), 80);
        // Reproductor.playMusic(1);

        // CARGAMOS EL ARRAY DE DATOS QUE SERA OBSERVADO EL CUAL ES CARGADO DEL FICHERO
        // SAVES.TXT
        if (misDatos == null) {
            misDatos = dl.load();
        }

        // Valores por defecto
        if (misDatos.isEmpty()) {
            misDatos.add(new Jugador("Player 1"));
        }

        // ASIGNAR ACCIONES Y ESTILO
        text_tittle.getStyleClass().add("tittle");
        text_tittle.setFont(tittleFont);
        button_exit.getStyleClass().add("buttonStartScene");
        button_play.getStyleClass().add("buttonStartScene");
        button_leaders.getStyleClass().add("buttonStartScene");

        button_exit.setOnAction(evnt -> exit());
        button_play.setOnAction(evnt -> play());
        button_leaders.setOnAction(evnt -> leaders());
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

    private void leaders() {
        try {
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/es/diego/buscaminas/leadersBoard.fxml"));
            Parent root = miCargador.load();
            LeadersBoardController controladorPersona = miCargador.<LeadersBoardController>getController();
            root.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            Scene scene = new Scene(root, 500, 650);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Records");

            // Mostrar la ventana
            stage.initModality(Modality.APPLICATION_MODAL);// La ventana se muestra modal
            stage.showAndWait();// Espera a que se cierre la segunda ventana
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // G&S
    public static ArrayList<Jugador> getMisDatos() {
        return misDatos;
    }

    public static void setMisDatos(ArrayList<Jugador> aMisDatos) {
        misDatos = aMisDatos;
    }

    public static DataLoader getDl() {
        return dl;
    }

    public static void setDl(DataLoader aDl) {
        dl = aDl;
    }
}
