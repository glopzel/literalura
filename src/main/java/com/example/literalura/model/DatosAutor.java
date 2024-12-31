package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String autor,
        @JsonAlias("birth_year") Number fechaNacimiento,
        @JsonAlias("death_year") Number fechaMuerte
) { }
