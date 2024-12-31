package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.autor();
        this.fechaNacimiento = (Integer) datosAutor.fechaNacimiento();
        this.fechaMuerte = (Integer) datosAutor.fechaMuerte();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String autor) {
        this.nombre = autor;
    }

    public Number getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Number getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return "\n------------- AUTOR -------------" +
                "\nNombre: " + nombre +
                "\nAño de nacimiento: " + fechaNacimiento +
                "\nAño de fallecimiento: " + fechaMuerte +
                "\n----------------------------------";
    }
}
