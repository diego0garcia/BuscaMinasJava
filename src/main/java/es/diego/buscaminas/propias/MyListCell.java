package es.diego.buscaminas.propias;

import javafx.scene.control.ListCell;

public class MyListCell extends ListCell<Jugador> {

         @Override
         
         protected void updateItem(Jugador item, boolean empty) {
                  super.updateItem(item, empty);
                  if (item == null || empty) {
                           setText(null);
                           setGraphic(null);
                  } else {
                           setText(item.getName() + ", " + item.getWins() + ", " + item.getDefeats());
                  }
         }
}