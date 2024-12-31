package com.example.literalura.principal;

import com.example.literalura.model.*;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.ConsumoApi;
import com.example.literalura.service.ConvierteDatos;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    private final String urlBase = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibroList = new ArrayList<>();
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<Libro> libros;
    private List<Autor> autores;
    private Optional<Libro> libroBuscado;

    public Principal (LibroRepository repositorioLibro, AutorRepository repositorioAutor) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    public void menu() {
        var opcion = -1;
        EstatusPrograma status = EstatusPrograma.CONTINUAR;

        while (status == EstatusPrograma.CONTINUAR) {
            var menuOpciones = """
                Elija la opción a través del número:
                1.- Buscar libro por titulo
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en un determinado año
                5.- Listar libros por idioma
                
                0.- Salir
                """;

            System.out.println(menuOpciones);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo(); // done
                    break;
                case 2:
                    mostrarLibrosRegistrados(); // done
                    break;
                case 3:
                    mostrarAutoresRegistrados(); // done
                    break;
                case 4:
                    mostrarAutoresVivosDurante();
                    break;
                case 5:
                    mostrarLibroPorIdioma(); // done
                    break;
                case 0:
                    System.out.println("Cerrando programa");
                    status = EstatusPrograma.DETENER;
                    break;
            }
        }
    }

    private void mostrarLibroPorIdioma() {
        System.out.println("""
                Selecciona un idioma:
                1.- Ingles
                2.- Español
                """);
        Integer idioma = Integer.valueOf(teclado.nextLine());

        if (idioma == 1) {
            libros = repositorioLibro.findByIdiomas("en");
        } else if (idioma == 2) {
            libros = repositorioLibro.findByIdiomas("es");
        } else {
            System.out.println("No se ingresó una opción válida");
        }

        libros.forEach(System.out::println);
    }

    private void mostrarAutoresVivosDurante() {
        System.out.println("Ingresa año: ");
        var fecha = teclado.nextInt();
        autores = repositorioAutor.findByFecha(fecha);
        autores.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        autores = repositorioAutor.findAll();
        autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
    }

    private void mostrarLibrosRegistrados() {
        libros = repositorioLibro.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);
    }

    private DatosResultado getDatosLibro(String nombreLibro) {
        nombreLibro = nombreLibro.replace(" ", "+").toLowerCase();

        var json = consumoApi.obtenerDatos(urlBase + nombreLibro);
        var datosLibroConvertido = conversor.obtenerDatos(json, DatosResultado.class);

        return datosLibroConvertido;
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el tìtulo del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine();

        var datos = getDatosLibro(nombreLibro);

        Autor autor = new Autor(datos.resultado().get(0).autor().get(0));
        Libro libro = new Libro(datos.resultado().get(0));

        libroBuscado = repositorioLibro.findByTituloContainsIgnoreCase(nombreLibro);

        if (libroBuscado.isPresent()) {
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("------LIBRO------");
            System.out.println("Titulo: " + datos.resultado().get(0).titulo());
            System.out.println("Autor: " + datos.resultado().get(0).autor().get(0).autor());
            System.out.println("Idiomas: " + datos.resultado().get(0).idiomas().get(0));
            System.out.println("Numero de descargas: " + datos.resultado().get(0).numeroDescargas());
            System.out.println("-----------------");

            repositorioAutor.save(autor);
            repositorioLibro.save(libro);
        }
    }
}

