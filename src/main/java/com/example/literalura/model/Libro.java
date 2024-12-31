package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idiomas;
    private Integer numeroDescargas;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idiomas().stream().limit(1).collect(Collectors.joining());
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    @Override
    public String toString() {
        return "------- LIBRO --------\n"+
                "Titulo: " + titulo + "\n"+
                "Idioma: " + idiomas + "\n"+
                "Numero de descargas: " + numeroDescargas + "\n"+
                "----------------------";
    }
}
