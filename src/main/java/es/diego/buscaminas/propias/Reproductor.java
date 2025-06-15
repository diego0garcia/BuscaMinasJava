package es.diego.buscaminas.propias;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Reproductor {

         private static MediaPlayer mp;
         private static String audio;

         public static void playMusic(int num) {

                  switch (num) {
                           case 1 -> {
                                    audio = "/audio/Anuel_AA_Naturaleza.mp3";
                           }
                           default -> {
                                    audio = "/audio/Anuel_AA_Naturaleza.mp3";
                           }
                  }

                  mp = new MediaPlayer(new Media(Reproductor.class.getResource(audio).toExternalForm()));
                  mp.setCycleCount(MediaPlayer.INDEFINITE);
                  mp.play();
         }

         public static void stopMusic() {
                  mp.stop();
         }
}
