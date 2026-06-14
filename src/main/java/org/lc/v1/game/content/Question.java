package org.lc.v1.game.content;

import java.util.List;

public class Question implements Content{
    private final String questionImagenRuta;
    private final List<String> opcionImagen;
    private final int respuestaCorrecta;

    public Question(String questionImagenRuta, List<String> opcionImagen, int respuestaCorrecta) {
        this.questionImagenRuta = questionImagenRuta;
        this.opcionImagen = opcionImagen;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public String getImagenRuta() {
        return questionImagenRuta;
    }

    @Override
    public boolean tieneOpciones() {
        return true;
    }

    public List<String> getOpcionImagen() {
        return opcionImagen;
    }

    public boolean esCorrecto(int selected) {
        return selected == respuestaCorrecta;
    }
}