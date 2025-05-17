package com.fied.guiaacademico.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Disciplina implements Parcelable {
    private int id;
    private String nome;
    private String codigo;
    private int periodo;
    private int creditos;
    private int cargaHoraria;
    private String ementa;
    private String bibliografia;
    private String preRequisitos;
    private String tipo;

    public Disciplina() {
    }

    public Disciplina(int id, String nome, String codigo, int periodo, int creditos, int cargaHoraria,
                     String ementa, String bibliografia, String preRequisitos, String tipo) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.periodo = periodo;
        this.creditos = creditos;
        this.cargaHoraria = cargaHoraria;
        this.ementa = ementa;
        this.bibliografia = bibliografia;
        this.preRequisitos = preRequisitos;
        this.tipo = tipo;
    }

    protected Disciplina(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        codigo = in.readString();
        periodo = in.readInt();
        creditos = in.readInt();
        cargaHoraria = in.readInt();
        ementa = in.readString();
        bibliografia = in.readString();
        preRequisitos = in.readString();
        tipo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(codigo);
        dest.writeInt(periodo);
        dest.writeInt(creditos);
        dest.writeInt(cargaHoraria);
        dest.writeString(ementa);
        dest.writeString(bibliografia);
        dest.writeString(preRequisitos);
        dest.writeString(tipo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Disciplina> CREATOR = new Creator<Disciplina>() {
        @Override
        public Disciplina createFromParcel(Parcel in) {
            return new Disciplina(in);
        }

        @Override
        public Disciplina[] newArray(int size) {
            return new Disciplina[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public String getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}