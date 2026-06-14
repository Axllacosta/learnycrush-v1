package org.lc.v1.game.content;

public class Example implements Content {
    private final String imagenRuta;

    public Example(String imagePath) {
        this.imagenRuta = imagePath;
    }

    @Override
    public String getImagenRuta() {
        return imagenRuta;
    }

    @Override
    public boolean tieneOpciones() {
        return false;
    }
}
