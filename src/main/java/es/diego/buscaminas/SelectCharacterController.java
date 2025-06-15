package es.diego.buscaminas;

import java.io.IOException;

import es.diego.buscaminas.propias.DataLoader;
import es.diego.buscaminas.propias.Jugador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectCharacterController implements Initializable {

         @FXML
         private Button button_remove;
         @FXML
         private Button button_add;
         @FXML
         private Button button_next;
         @FXML
         private StackPane stackPane_main;
         @FXML
         private Button button_back;
         @FXML
         private TableView<Jugador> tableView_main;
         @FXML
         private TableColumn<Jugador, String> tableClumn_1;
         @FXML
         private TableColumn<Jugador, Number> tableClumn_2;
         @FXML
         private TableColumn<Jugador, Number> tableClumn_3;
         @FXML
         private Button buttonDificulty1;
         @FXML
         private Button buttonDificulty2;
         @FXML
         private Button buttonDificulty3;
         @FXML
         private Button buttonDificulty4;
         @FXML
         private Text text_informationAboutDificulty;
         @FXML
         private HBox hbox_containerDificulties;

         private static final DataLoader dl = new DataLoader();
         private ObservableList<Jugador> observableListAutores;
         private static ArrayList<Jugador> misDatos;
         private static IntegerProperty dificulty = new SimpleIntegerProperty();
         private static int minas;
         private static int filas;
         private static int columnas;
         private static Jugador player;

         // INICIALIZADOR
         @Override
         public void initialize(URL url, ResourceBundle rb) {
                  // CARGAMOS EL ARRAY DE DATOS QUE SERA OBSERVADO EL CUAL ES CARGADO DEL FICHERO SAVES.TXT

                  setDificulty(-1);

                  if (misDatos == null) {
                           misDatos = dl.load();
                  }

                  // Valores por defecto
                  if (misDatos.isEmpty()) {
                           misDatos.add(new Jugador("Player 1"));
                  }

                  // Hacemos observable el array
                  observableListAutores = FXCollections.observableArrayList(misDatos);
                  tableView_main.setItems(observableListAutores);

                  //Creamos las columnas
                  tableClumn_1.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
                  tableClumn_2.setCellValueFactory(cellData -> cellData.getValue().WinsProperty());
                  tableClumn_3.setCellValueFactory(cellData -> cellData.getValue().DefeatsProperty());

                  //Asignamos eventos a los botones
                  stackPane_main.setOnMouseClicked(evnt -> cleanSelected());
                  button_remove.setOnAction(evnt -> deleteAction());
                  button_back.setOnAction(evnt -> backAction());
                  button_add.setOnAction(evnt -> addAction());
                  buttonDificulty1.setOnAction(evnt -> setDificulty(1));
                  buttonDificulty2.setOnAction(evnt -> setDificulty(2));
                  buttonDificulty3.setOnAction(evnt -> setDificulty(3));
                  buttonDificulty4.setOnAction(evnt -> addDificulty());

                  //Permitir seleccion multiple
                  tableView_main.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                  //Asignamos los bings a las propiedades observadas
                  button_remove.disableProperty().bind(Bindings.equal(-1, tableView_main.getSelectionModel().selectedIndexProperty()));
                  button_next.disableProperty().bind(Bindings.or(Bindings.equal(-1, tableView_main.getSelectionModel().selectedIndexProperty()), Bindings.or(Bindings.equal(-1, dificulty), Bindings.not(Bindings.equal(1, Bindings.size(tableView_main.getSelectionModel().getSelectedItems()))))));
                  button_next.setOnAction(evnt -> nextAction());
                  tableView_main.getSelectionModel().selectedIndexProperty().addListener(evnt -> {
                           setPlayer(tableView_main.getSelectionModel().getSelectedItem());
                  });

                  dificulty.addListener(evnt -> {
                           //Quitar clase del seleccionado
                           for (int i = 0; i < hbox_containerDificulties.getChildren().size(); i++) {
                                    if (hbox_containerDificulties.getChildren().get(i).getStyleClass().contains("selected")) {
                                             hbox_containerDificulties.getChildren().get(i).getStyleClass().remove("selected");
                                    }
                           }

                           //Añadir clase seleccionado
                           hbox_containerDificulties.getChildren().get(getDificulty() - 1).getStyleClass().add("selected");

                           //Actualizar la dificultad
                           switch (getDificulty()) {
                                    case 1 -> {
                                             setColumnas(8);
                                             setFilas(8);
                                             setMinas(10);
                                    }
                                    case 2 -> {
                                             setColumnas(16);
                                             setFilas(16);
                                             setMinas(40);
                                    }
                                    case 3 -> {
                                             setColumnas(16);
                                             setFilas(30);
                                             setMinas(99);
                                    }
                                    case 4 -> {

                                    }
                                    default ->
                                             throw new AssertionError();
                           }

                           //Actualizar el texto de informacion
                           text_informationAboutDificulty.setText("Dificultad: " + ((getDificulty() == 1) ? "Principiante" : (getDificulty() == 2) ? "Intermedio" : (getDificulty() == 3) ? "Experto" : "Personalizado") + " | Tablero: " + getFilas() + "x" + getColumnas() + " | Minas: " + getMinas());
                  });
         }

         // AÑADIR
         private void addAction() {
                  try {
                           // Cargar escena
                           FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/es/diego/buscaminas/addPlayer.fxml"));
                           Parent root = miCargador.load();
                           AddPlayerController controladorPersona = miCargador.<AddPlayerController>getController();
                           root.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

                           // Creamos nuevo jugador donde se asignaran sus valores
                           Jugador player = new Jugador("");
                           controladorPersona.initPlayer(player);
                           Scene scene = new Scene(root, 600, 500);
                           Stage stage = new Stage();
                           stage.setScene(scene);
                           stage.setTitle("Añadir Jugador");

                           // Mostrar la ventana
                           stage.initModality(Modality.APPLICATION_MODAL);// La ventana se muestra modal
                           stage.showAndWait();// Espera a que se cierre la segunda ventana

                           // Limpiamos seleccionado
                           cleanSelected();

                           // Actualizar table view
                           if (!controladorPersona.getCancelar()) {
                                    if ((!controladorPersona.getJugador().getName().isEmpty())) {
                                             observableListAutores.add(controladorPersona.getJugador());
                                             tableView_main.refresh();
                                             misDatos.add(controladorPersona.getJugador());
                                             dl.save(misDatos);
                                    }
                           }
                  } catch (IOException ex) {
                           System.out.println("ERROR AL CARGAR VENTANA AÑADIR SelectController-addAction");
                  }
         }

         // DIFICULTAD PERSONALIZADA
         private void addDificulty() {
                  try {
                           // Cargar escena
                           FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/es/diego/buscaminas/addCustomDificulty.fxml"));
                           Parent root = miCargador.load();
                           AddCustomDificultyController controladorPersona = miCargador.<AddCustomDificultyController>getController();
                           root.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

                           Scene scene = new Scene(root, 600, 500);
                           Stage stage = new Stage();
                           stage.setScene(scene);
                           stage.setTitle("Dificultad Personalizada");

                           // Mostrar la ventana
                           stage.initModality(Modality.APPLICATION_MODAL);// La ventana se muestra modal
                           stage.showAndWait();// Espera a que se cierre la segunda ventana

                           // Actualizar table view
                           setFilas(controladorPersona.getRow());
                           setColumnas(controladorPersona.getColumn());
                           setMinas(controladorPersona.getBombs());

                           setDificulty(4);
                  } catch (IOException ex) {
                           System.out.println("ERROR AL CARGAR VENTANA DIFICULTAD SelectController-addDificulty");
                  }
         }

         //BORRAR
         private void deleteAction() {
                  //Comprobamos que hay algun seleccionado
                  if (tableView_main.getSelectionModel().getSelectedItem() != null) {
                           ObservableList<Jugador> itemsSeleccionados = tableView_main.getSelectionModel().getSelectedItems();

                           if (!itemsSeleccionados.isEmpty()) {
                                    observableListAutores.removeAll(itemsSeleccionados);
                                    misDatos = new ArrayList<>(observableListAutores);
                                    dl.save(misDatos);
                                    cleanSelected();
                           }
                  }
         }

         // LIMPIAR SELECCIONADOS
         private void cleanSelected() {
                  tableView_main.getSelectionModel().clearSelection();
         }

         //VOLVER ATRAS
         private void backAction() {
                  try {
                           App.setRoot("game");
                  } catch (IOException e) {
                           System.err.println("ERROR AL CARGAR FXML SelectCharacterController-back");
                  }
         }

         //SIGUIENETE PANTALLA
         private void nextAction() {
                  try {
                           App.setRoot("board");
                  } catch (IOException e) {
                           System.err.println("ERROR AL CARGAR FXML SelectCharacterController-next");
                  }
         }

         //G&S
         public static ArrayList<Jugador> getMisDatos() {
                  return misDatos;
         }

         public static void setMisDatos(ArrayList<Jugador> aMisDatos) {
                  misDatos = aMisDatos;
         }

         public static int getMinas() {
                  return minas;
         }

         public static void setMinas(int aMinas) {
                  minas = aMinas;
         }

         public static int getFilas() {
                  return filas;
         }

         public static void setFilas(int aFilas) {
                  filas = aFilas;
         }

         public static int getColumnas() {
                  return columnas;
         }

         public static void setColumnas(int aColumnas) {
                  columnas = aColumnas;
         }

         public static final IntegerProperty DificultyProperty() {
                  return dificulty;
         }

         public static final java.lang.Integer getDificulty() {
                  return DificultyProperty().get();
         }

         public final void setDificulty(final java.lang.Integer dificulty) {
                  this.DificultyProperty().set(dificulty);
         }

         public static Jugador getPlayer() {
                  return player;
         }

         public static void setPlayer(Jugador aPlayer) {
                  player = aPlayer;
         }

         public static DataLoader getDl() {
                  return dl;
         }

}
