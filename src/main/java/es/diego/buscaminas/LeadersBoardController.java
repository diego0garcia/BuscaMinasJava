package es.diego.buscaminas;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import es.diego.buscaminas.propias.Jugador;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView imageView_2;
    @FXML
    private ImageView imageView_1;
    @FXML
    private ImageView imageView_3;

    private ArrayList<Jugador> aux = new ArrayList<>(GameController.getMisDatos());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        orderArray();

        if ((!getAux().isEmpty())) {
            text_p1.setText(getAux().get(0).getName());
            text_d1.setText("Victorias: " + getAux().get(0).getWins());
            imageView_1.setImage(new Image(new File(getAux().get(0).getPahtImagen()).toURI().toString()));
        } else {
            text_p1.setText("");
            text_d1.setText("");
        }

        if ((getAux().size() > 1)) {
            text_p2.setText(getAux().get(1).getName());
            text_d2.setText("Victorias: " + getAux().get(1).getWins());
            imageView_2.setImage(new Image(new File(getAux().get(1).getPahtImagen()).toURI().toString()));
        } else {
            text_p2.setText("");
            text_d2.setText("");
        }

        if ((getAux().size() > 2)) {
            text_p3.setText(getAux().get(2).getName());
            text_d3.setText("Victorias: " + getAux().get(2).getWins());
            imageView_3.setImage(new Image(new File(getAux().get(2).getPahtImagen()).toURI().toString()));
        } else {
            text_p3.setText("");
            text_d3.setText("");
        }

        button_back.setOnAction(evnt -> {
            Node n = (Node) evnt.getSource();
            n.getScene().getWindow().hide();
        });
    }

    public void orderArray() {
        ArrayList<Jugador> orderedList = getAux();
        orderedList.sort(Comparator.comparingInt((Jugador::getWins)).reversed());
        setAux(orderedList);
    }
    
    // G&S

    public ArrayList<Jugador> getAux() {
        return aux;
    }

    public void setAux(ArrayList<Jugador> aux) {
        this.aux = aux;
    }
    }
