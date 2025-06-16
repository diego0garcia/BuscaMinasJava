package es.diego.buscaminas.propias;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty wins = new SimpleIntegerProperty();
    private final IntegerProperty defeats = new SimpleIntegerProperty();
    private final StringProperty pahtImagen = new SimpleStringProperty();

    public Jugador(String name, String path) {
        this.name.setValue(name);
        this.wins.setValue(0);
        this.defeats.setValue(0);
        this.pahtImagen.setValue(path);
    }

    public Jugador(String name, Integer wins, Integer defeats, String path) {
        this.name.setValue(name);
        this.wins.setValue(wins);
        this.defeats.setValue(defeats);
        this.pahtImagen.setValue(path);
    }

    private void addWin() {
        setWins(getWins() + 1);
    }

    private void addDefeat() {
        setDefeats(getDefeats() + 1);
    }

    // G&S
    // NAME
    public final StringProperty NameProperty() {
        return name;
    }

    public final java.lang.String getName() {
        return this.NameProperty().get();
    }

    public final void setName(final java.lang.String Nombre) {
        this.NameProperty().set(Nombre);
    }

    // WINS
    public final IntegerProperty WinsProperty() {
        return wins;
    }

    public final java.lang.Integer getWins() {
        return this.WinsProperty().get();
    }

    public final void setWins(final java.lang.Integer wins) {
        this.WinsProperty().set(wins);
    }

    // DEFEATS
    public final IntegerProperty DefeatsProperty() {
        return defeats;
    }

    public final java.lang.Integer getDefeats() {
        return this.DefeatsProperty().get();
    }

    public final void setDefeats(final java.lang.Integer wins) {
        this.DefeatsProperty().set(wins);
    }

    // PATH IMAGE
    public StringProperty PahtImagenProperty() {
        return pahtImagen;
    }

    public final java.lang.String getPahtImagen() {
        return this.PahtImagenProperty().get();
    }

    public final void setPahtImagen(final java.lang.String pathImage) {
        this.PahtImagenProperty().set(pathImage);
    }
}