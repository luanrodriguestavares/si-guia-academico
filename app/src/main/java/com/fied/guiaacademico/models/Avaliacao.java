package com.fied.guiaacademico.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Avaliacao {
    private int id;
    private int disciplinaId;
    private float nota;
    private String comentario;
    private long dataTimestamp;

    public Avaliacao() {
    }

    public Avaliacao(int id, int disciplinaId, float nota, String comentario, long dataTimestamp) {
        this.id = id;
        this.disciplinaId = disciplinaId;
        this.nota = nota;
        this.comentario = comentario;
        this.dataTimestamp = dataTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getDataTimestamp() {
        return dataTimestamp;
    }

    public void setDataTimestamp(long dataTimestamp) {
        this.dataTimestamp = dataTimestamp;
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date(dataTimestamp));
    }

    public long getData() {
        return getDataTimestamp();
    }

    public void setData(long data) {
        setDataTimestamp(data);
    }
}