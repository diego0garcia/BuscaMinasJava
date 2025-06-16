package es.diego.buscaminas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCustomDificultyController implements Initializable {

    @FXML
    private Button button_decline;
    @FXML
    private Button button_accept;
    @FXML
    private TextField textField_row;
    @FXML
    private TextField textField_column;
    @FXML
    private TextField textField_bomb;

    private boolean decline;
    private int row;
    private int column;
    private int bombs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button_accept.setOnAction(evnt -> accept(evnt));
        button_decline.setOnAction(evnt -> close(evnt));
    }

    private void accept(ActionEvent evnt) {

        if (!textField_bomb.getText().trim().equals("") && !textField_column.getText().trim().equals("") && !textField_row.getText().trim().equals("")) {
            try {
                int totalBombs = (Integer.parseInt(textField_row.getText().trim()) * Integer.parseInt(textField_column.getText().trim()));
                System.out.println(totalBombs);
                if (!(Integer.parseInt(textField_bomb.getText().trim()) < 1) && !(Integer.parseInt(textField_column.getText().trim()) < 1) && !(Integer.parseInt(textField_row.getText().trim()) < 1)) {
                    if (totalBombs > Integer.parseInt(textField_bomb.getText())) {
                        if (Integer.parseInt(textField_column.getText().trim()) <= 20 && Integer.parseInt(textField_row.getText().trim()) <= 30) {
                            setBombs(Integer.parseInt(textField_bomb.getText().trim()));
                            setRow(Integer.parseInt(textField_row.getText().trim()));
                            setColumn(Integer.parseInt(textField_column.getText().trim()));
                            setDecline(false);
                            Node n = (Node) evnt.getSource();
                            n.getScene().getWindow().hide();
                        } else {
                            Alert alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setTitle("Excepción");
                            alerta.setHeaderText("ERROR");
                            alerta.setContentText("Valores demasiado grandes.\nMÁXIMO: 30*20");
                            alerta.show();
                        }
                    } else {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Excepción");
                        alerta.setHeaderText("ERROR");
                        alerta.setContentText("El número de minas el mayor al de casillas totales");
                        alerta.show();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Excepción");
                    alerta.setHeaderText("ERROR");
                    alerta.setContentText("Números introducidos inválidos");
                    alerta.show();
                }
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Excepción");
                alerta.setHeaderText("ERROR");
                alerta.setContentText("Hay campos que no son numeros");
                alerta.show();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Excepción");
            alerta.setHeaderText("ERROR");
            alerta.setContentText("Hay campos vacíos");
            alerta.show();
        }
    }

    private void close(ActionEvent evnt) {
        setDecline(true);
        Node n = (Node) evnt.getSource();
        n.getScene().getWindow().hide();
    }

    // G&S
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public boolean isDecline() {
        return decline;
    }

    public void setDecline(boolean decline) {
        this.decline = decline;
    }

}
