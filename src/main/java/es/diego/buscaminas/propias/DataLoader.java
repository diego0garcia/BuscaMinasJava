package es.diego.buscaminas.propias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DataLoader {

         private static ArrayList<Jugador> misDatos = new ArrayList<>();
         private static final File SAVE_FILE = new File("save.txt");

         public DataLoader() {
                  try {
                           if (!SAVE_FILE.exists()) {
                                    SAVE_FILE.createNewFile();
                           }
                  } catch (IOException ex) {
                           System.out.println("ERROR AL CREAR");
                  }
         }

         public ArrayList<Jugador> load() {
                  //Cargamos los datos del fichero al array
                  try (BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE))) {
                           String linea;

                           while ((linea = br.readLine()) != null) {
                                    String[] partes = linea.split(",");
                                    String name = partes[0];
                                    String wins = partes[1];
                                    String defeats = partes[2];
                                    getMisDatos().add(new Jugador((name), Integer.valueOf(wins), Integer.valueOf(defeats)));
                           }

                           br.close();

                  } catch (IOException e) {
                           System.out.println("ERROR AL CARGAR");
                  }
                  return getMisDatos();
         }

         public void save(ArrayList<Jugador> misDatosMod) {
                  try {
                           setMisDatos(misDatosMod);
                           PrintWriter writer = new PrintWriter(SAVE_FILE);
                           for (Jugador misDato : misDatos) {
                                    writer.printf("%s,%s,%s\n", misDato.getName(), misDato.getWins(), misDato.getDefeats());
                           }
                           writer.close();
                  } catch (IOException e) {
                           e.printStackTrace();
                  }
         }

         private ArrayList<Jugador> getMisDatos() {
                  return misDatos;
         }

         private void setMisDatos(ArrayList<Jugador> aMisDatos) {
                  misDatos = aMisDatos;
         }
}
