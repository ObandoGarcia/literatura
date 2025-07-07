package com.obando.literatura.Model;

import com.obando.literatura.DTO.DatosLibro;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idiomas;
    private Double numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datos, Autor autor) {
        this.titulo = datos.titulo();
        this.autor = autor;
        this.idiomas = datos.idiomas().get(0);
        this.numeroDeDescargas = datos.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "Datos del libro registrado en la base de datos: " +
                ", titulo: " + titulo +
                ", autores: " + autor +
                ", idiomas: " + idiomas +
                ", numeroDeDescargas: " + numeroDeDescargas;
    }
}
