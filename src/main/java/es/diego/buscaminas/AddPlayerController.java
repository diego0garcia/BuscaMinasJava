package es.diego.buscaminas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import es.diego.buscaminas.propias.Jugador;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class AddPlayerController implements Initializable {

    @FXML
    private TextField textField_name;
    @FXML
    private Button button_decline;
    @FXML
    private Button button_accept;
    @FXML
    private VBox Hbox_img1;
    @FXML
    private VBox Hbox_img2;
    @FXML
    private VBox Hbox_img3;
    @FXML
    private VBox Hbox_img4;
    @FXML
    private ImageView ImageView_customImage;
    @FXML
    private Button button_customImage;
    @FXML
    private HBox vBox_photoPlayers;

    private boolean cancelar;
    private BooleanProperty imgSelected = new SimpleBooleanProperty(false);
    private Jugador jugador;
    private String path;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCancelar(true);
        setImgSeleted(false);

        button_accept.setOnAction(evnt -> accept(evnt));
        button_decline.setOnAction(evnt -> close(evnt));
        button_customImage.setOnAction(evnt -> selectCustomImage(evnt));

        Hbox_img1.setOnMouseClicked(evnt -> {
            setPath("src/main/resources/imgs/player1.png");
            cleanSelected();
            setImgSeleted(true);
            Hbox_img1.getStyleClass().add("photoPlayerSelected");
        });
        Hbox_img2.setOnMouseClicked(evnt -> {
            setPath("src/main/resources/imgs/player2.png");
            cleanSelected();
            setImgSeleted(true);
            Hbox_img2.getStyleClass().add("photoPlayerSelected");
        });
        Hbox_img3.setOnMouseClicked(evnt -> {
            setPath("src/main/resources/imgs/player3.png");
            cleanSelected();
            setImgSeleted(true);
            Hbox_img3.getStyleClass().add("photoPlayerSelected");
        });
        Hbox_img4.setOnMouseClicked(evnt -> {
            setPath("src/main/resources/imgs/player4.png");
            cleanSelected();
            setImgSeleted(true);
            Hbox_img4.getStyleClass().add("photoPlayerSelected");
        });

        button_accept.disableProperty().bind(imgSelected.not());
    }

    public void initPlayer(Jugador player) {
        textField_name.setText(player.getName());
    }

    private void cleanSelected() {
        setImgSeleted(false);
        ImageView_customImage.setImage(null);
        for (int i = 0; i < vBox_photoPlayers.getChildren().size(); i++) {
            vBox_photoPlayers.getChildren().get(i).getStyleClass().remove("photoPlayerSelected");
        }
    }

    private void selectCustomImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Titulo: Cambiar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        cleanSelected();

        if (selectedFile != null) {
            button_customImage.getStyleClass().add("photoPlayerSelected");
            setPath(selectedFile.getAbsolutePath());
            ImageView_customImage.setImage(new Image(selectedFile.toURI().toString()));
            setImgSeleted(true);
        }
        if (button_customImage.getGraphic() == null) {
            setImgSeleted(false);
        }
    }

    private void accept(ActionEvent evnt) {
        // COMPROBAMOS QUE TODOS LOS CAMPOS ESTEN LLENOS
        if (!textField_name.getText().trim().equals("")) {
            if (validName(textField_name.getText()) == true) {
                setCancelar(false);
                setJugador(new Jugador(textField_name.getText().trim(), getPath()));
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

    // COMPROBAR DUPLICADOS
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
        return jugador;
    }

    public void setJugador(Jugador persona) {
        this.jugador = persona;
    }

    public BooleanProperty imgSelectedProperty() {
        return imgSelected;
    }

    public boolean isImgSeleted() {
        return imgSelected.get();
    }

    public void setImgSeleted(boolean imgSelected) {
        this.imgSelected.set(imgSelected);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
