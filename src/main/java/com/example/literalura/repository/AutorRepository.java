package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND (a.fechaMuerte >= :fecha OR a.fechaMuerte IS NULL)")
    List<Autor> findByFecha(Integer fecha);
}
