package com.example.literalura.repository;

import com.example.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idiomas ILIKE %:idioma%")
    List<Libro> findByIdiomas(String idioma);
}
