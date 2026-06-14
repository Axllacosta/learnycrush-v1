package org.lc.v1.game.levels;

public class LevelData {
    private final int nivelNumero;
    private LevelState estado;
    private int estrellasObtenidas;

    public LevelData(int nivelNumero, LevelState estado, int estrellasObtenidas) {
        this.nivelNumero = nivelNumero;
        this.estado = estado;
        this.estrellasObtenidas = estrellasObtenidas;
    }

    public int getNivelNumero() {
        return nivelNumero;
    }

    public LevelState getEstado() {
        return estado;
    }

    public int getEstrellasObtenidas() {
        return estrellasObtenidas;
    }

    public void setEstado(LevelState estado) {
        this.estado = estado;
    }

    public void setEstrellasObtenidas(int estrellas) {
        this.estrellasObtenidas = estrellas;
    }

    public boolean esAccesible() {
        return estado != LevelState.BLOQUEADO;
    }
}
