package es.diego.buscaminas;

import es.diego.buscaminas.propias.DataLoader;
import es.diego.buscaminas.propias.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BoardController implements Initializable {

         @FXML
         private Text textField_info;
         @FXML
         private GridPane gridPane_board;
         @FXML
         private HBox hBox_BoardContainer;
         @FXML
         private Text text_infoDebuger;

         private GridPane visualBoard = new GridPane();
         private static boolean win;
         private int fila = SelectCharacterController.getFilas();
         private int columna = SelectCharacterController.getColumnas();
         private int minas = SelectCharacterController.getMinas();
         private int[][] numberBoard = new int[fila][columna];
         private boolean[][] revaledBoard = new boolean[fila][columna];
         private Jugador player = SelectCharacterController.getPlayer();

         //IMAGENES
         Image bombImage = new Image((getClass().getResource("/imgs/bomb.png")).toExternalForm(), 20, 20, true, true);
         Image explosionImage = new Image((getClass().getResource("/imgs/explosion.png")).toExternalForm(), 20, 20, true, true);
         Image flagImage = new Image((getClass().getResource("/imgs/flag.png")).toExternalForm(), 20, 20, true, true);

         @Override
         public void initialize(URL url, ResourceBundle rb) {
                  hBox_BoardContainer.getChildren().remove(0);

                  visualBoard = createBoard();

                  hBox_BoardContainer.getChildren().add(visualBoard);
         }

         //ACCIONES SOBRE LA CASILLA
         private void cellActions(MouseEvent evntM) {
                  Button btn = (Button) evntM.getSource();
                  btn.getStyleClass().removeAll("cellDefault");

                  //Click izquierdo
                  if (evntM.getButton().equals(MouseButton.PRIMARY)) {
                           //Comprueba si tiene bomba
                           if (btn.getGraphic() != null) {
                                    clickBomb(btn);
                           } else {
                                    clickEmpty(btn);
                           }
                           //Desactiva esa casilla
                           btn.setDisable(true);
                  }

                  //Click derecho
                  if (evntM.getButton().equals(MouseButton.SECONDARY)) {
                           clickFlag(btn);
                  }
         }

         //CLICK BOMBA
         private void clickBomb(Button btn) {
                  btn.getGraphic().getStyleClass().removeAll("hidden");
                  btn.getStyleClass().add("cellDefeat");
                  btn.setGraphic(new ImageView(explosionImage));

                  defeat();
         }

         //CLICK CASILLA VACIA
         private void clickEmpty(Button btn) {
                  btn.getStyleClass().add("cellReveal");

                  String coordenadas[] = btn.getId().split(",");
                  int x = Integer.parseInt(coordenadas[0]);
                  int y = Integer.parseInt(coordenadas[1]);

                  desvelarRecursivo(getNumberBoard(), getRevaledBoard(), x, y);
         }

         private void desvelarRecursivo(int[][] matriz, boolean[][] visited, int fila, int columna) {
                  //Validar límites de la matriz
                  if (fila < 0 || fila >= matriz.length || columna < 0 || columna >= matriz[0].length) {
                           return;
                  }

                  if (visited[fila][columna] || matriz[fila][columna] > 0) {
                           return;
                  }

                  visited[fila][columna] = true;

                  String idNewButton = ("#" + (fila) + "," + (columna));
                  Button btnAux = (Button) visualBoard.lookup(idNewButton);
                  btnAux.getStyleClass().add("cellReveal");
                  btnAux.setDisable(true);

                  //Recursividad
                  desvelarRecursivo(matriz, visited, fila - 1, columna); //ARRIBA
                  desvelarRecursivo(matriz, visited, fila + 1, columna); //ABAJO
                  desvelarRecursivo(matriz, visited, fila, columna - 1); //IZQUIERDA
                  desvelarRecursivo(matriz, visited, fila, columna + 1); //DERECHA
         }

         //CLICK BANDERA
         private void clickFlag(Button btn) {
                  if (btn.getStyleClass().contains("cellMark")) {
                           btn.getStyleClass().removeAll("cellMark");
                           btn.getStyleClass().add("cellDefault");
                           btn.setGraphic(null);

                  } else {
                           btn.getStyleClass().add("cellMark");
                           btn.setGraphic(new ImageView(flagImage));
                           if (checkFlags()) {
                                    win();
                           }
                  }
         }

         //COMPRUEBA SI ESTAN TDOAS LAS MINAS MARCADAS
         private boolean checkFlags() {
                  int counterCorrects = 0;

                  for (int i = 0; i < getNumberBoard().length; i++) {
                           for (int j = 0; j < getNumberBoard()[0].length; j++) {
                                    Button btnAux = (Button) visualBoard.lookup(("#" + (i) + "," + (j)));
                                    if (btnAux.getStyleClass().contains("cellMark") && getNumberBoard()[i][j] == -1) {
                                             counterCorrects++;
                                    }
                           }
                  }

                  System.out.println("Total Marcadas" + counterCorrects);
                  return counterCorrects >= minas;
         }

         //REVELA TODAS LAS BOMBAS
         private void revealAllBombs() {
                  for (int i = 0; i < visualBoard.getChildren().size(); i++) {
                           Button btn = (Button) visualBoard.getChildren().get(i);
                           if ((btn.getGraphic() != null)) {
                                    btn.getStyleClass().add("cellBomb");
                                    btn.getGraphic().getStyleClass().removeAll("hidden");
                           }
                  }
         }

         //GANAR
         private void win() {
                  visualBoard.setDisable(true);
                  text_infoDebuger.setText("VICTORIA");
                  player.setWins(player.getWins() + 1);
                  save();
                  setWin(true);
                  nextAction();
         }

         //PERDER
         private void defeat() {
                  revealAllBombs();
                  visualBoard.setDisable(true);
                  text_infoDebuger.setText("DERROTA");
                  player.setDefeats(player.getDefeats() + 1);
                  save();
                  setWin(false);
                  nextAction();
         }

         //SIGUIENETE PANTALLA
         private void nextAction() {
                  PauseTransition wait = new PauseTransition(Duration.seconds(2));
                  wait.setOnFinished(evnt -> {
                           try {
                                    App.setRoot("finalScreen");
                           } catch (IOException e) {
                                    System.err.println("ERROR AL CARGAR FXML SelectCharacterController-next");
                           }
                  });
                  wait.play();
         }

         //GUARDAR
         private void save() {
                  GameController.getDl().save(SelectCharacterController.getMisDatos());
         }

         //CREA EL TABLERO
         private GridPane createBoard() {
                  GridPane boardAux = new GridPane();
                  int[][] matriz = new int[fila][columna];

                  int counter = 0;

                  //Crear posicion bombas
                  do {
                           int x = (int) (Math.random() * fila);
                           int y = (int) (Math.random() * columna);

                           matriz[x][y] = -1;
                           counter++;

                  } while (counter != minas);

                  boolean[][] booleanBoard = new boolean[fila][columna];
                  for (int i = 0; i < getRevaledBoard().length; i++) {
                           for (int j = 0; j < getRevaledBoard()[0].length; j++) {
                                    booleanBoard[i][j] = false;
                           }
                  }

                  setRevaledBoard(booleanBoard);

                  //Rellena la matriz
                  for (int x = 0; x < getNumberBoard().length; x++) {
                           for (int y = 0; y < getNumberBoard()[0].length; y++) {
                                    if (matriz[x][y] != -1) {

                                             Integer bombCounter = 0;

                                             for (int i = -1; i < 2; i++) {
                                                      for (int j = -1; j < 2; j++) {
                                                               if (x + i >= 0 && y + j >= 0 && x + i < matriz.length && y + j < matriz[0].length) {
                                                                        if (matriz[x + i][y + j] == -1) {
                                                                                 bombCounter++;
                                                                        }
                                                               }
                                                      }
                                             }

                                             matriz[x][y] = bombCounter;
                                    }
                           }
                  }

                  //Crear tablero
                  for (int i = 0; i < getNumberBoard().length; i++) {
                           for (int j = 0; j < getNumberBoard()[0].length; j++) {
                                    String idString = (i + "," + j);
                                    Button btn = new Button();
                                    btn.setId(idString);
                                    btn.getStyleClass().add("ceell");
                                    btn.getStyleClass().add("cellDefault");
                                    btn.setOnMouseClicked(evnt -> cellActions(evnt));

                                    //Colocar bombas en el tablero
                                    if (matriz[i][j] == (-1)) {
                                             ImageView bombImageView = new ImageView(bombImage);
                                             bombImageView.getStyleClass().add("hidden");
                                             btn.setGraphic(bombImageView);
                                    } else {
                                             btn.setText(Integer.toString(matriz[i][j]));
                                    }
                                    boardAux.add(btn, j, i);
                                    //System.out.println(btn.getId());
                           }
                  }

                  for (int i = 0; i < getNumberBoard().length; i++) {
                           for (int j = 0; j < getNumberBoard()[0].length; j++) {
                                    System.out.printf("%3d", matriz[i][j]);
                           }
                           System.out.println("");
                  }

                  setNumberBoard(matriz);
                  return boardAux;
         }

         //G&S
         public int[][] getNumberBoard() {
                  return numberBoard;
         }

         public void setNumberBoard(int[][] numberBoard) {
                  this.numberBoard = numberBoard;
         }

         public int getFila() {
                  return fila;
         }

         public void setFila(int fila) {
                  this.fila = fila;
         }

         public int getColumna() {
                  return columna;
         }

         public void setColumna(int columna) {
                  this.columna = columna;
         }

         public int getMinas() {
                  return minas;
         }

         public void setMinas(int minas) {
                  this.minas = minas;
         }

         public boolean[][] getRevaledBoard() {
                  return revaledBoard;
         }

         public void setRevaledBoard(boolean[][] revaledBoard) {
                  this.revaledBoard = revaledBoard;
         }

         public static boolean isWin() {
                  return win;
         }

         public static void setWin(boolean aWin) {
                  win = aWin;
         }
}
