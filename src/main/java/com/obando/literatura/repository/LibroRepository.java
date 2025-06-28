package com.obando.literatura.repository;

import com.obando.literatura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByIdiomas(String idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDeDescargas ASC LIMIT 5")
    List<Libro> findTop5ByDownloadNumber();
}
