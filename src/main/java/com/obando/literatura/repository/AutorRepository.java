package com.obando.literatura.repository;

import com.obando.literatura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE %:nombre%")
    Autor findByNombrePersonalizado(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento < :anio AND a.fechaDeFallecimiento > :anio")
    List<Autor> obtenerAutoresVivosEnUnaAnio(Double anio);
}