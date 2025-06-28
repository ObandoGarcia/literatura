package com.obando.literatura.principal;

import com.obando.literatura.DTO.Datos;
import com.obando.literatura.DTO.DatosAutor;
import com.obando.literatura.DTO.DatosLibro;
import com.obando.literatura.Model.Autor;
import com.obando.literatura.Model.Libro;
import com.obando.literatura.repository.AutorRepository;
import com.obando.literatura.repository.LibroRepository;
import com.obando.literatura.service.ConsumoAPI;
import com.obando.literatura.service.ConvierteDatos;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/";

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private String json;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu(){
        int opcion = -1;

        while (opcion != 0){
            String menu = """
                    
                    ****************** ADMINISTRADOR DIGITAL DE LIBROS ******************
                    OPCIONES:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos de un anio
                    5 - Listar libros por idioma
                    6 - Mostrar estadisticas de los libros
                    7 - Buscar autor por nombre
                    8 - Top 5 libros mas descargados
                    0 - Salir de la aplicacion
                    
                    """;

            System.out.println(menu);

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarListadoDeLibros();
                    break;
                case 3:
                    mostrarListadoDeAutoresRegistrados();
                    break;
                case 4:
                    System.out.println("Ingrese el anio en que desea verificar los autores vivos: ");
                    Double anio = scanner.nextDouble();
                    mostrarAutoresVivosEnUnAnio(anio);
                    break;
                case 5:
                    String menuIdiomas = """
                            en - ingles
                            es - espaniol
                            fi - finlandes
                            fr - frances
                            """;

                    System.out.println("Ingrese el idioma del libro a filtrar. Seleciona de la lista: ");
                    System.out.println(menuIdiomas);
                    String idiomaABuscar = scanner.nextLine();

                    mostrarLibrosPorIdioma(idiomaABuscar);

                    break;
                case 6:
                    System.out.println("---------------Estadisticas de los libros --------------");
                    mostrarEstadisticasGeneralesDeLosLibros();
                    break;
                case 7:
                    System.out.println("Busqueda de autores por nombre: ");
                    System.out.println("Ingrese el nombre del autor a buscar: ");
                    String nombreAutor = scanner.nextLine();

                    mostrarAutorPorNombre(nombreAutor);

                    break;
                case 8:
                    System.out.println("Top 5 libros mas descargados: ");

                    top5Libros();

                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion....");
                    break;
                default:
                    System.out.println("Has ingresado una opcion no valida!");
            }
        }
    }

    private void buscarLibroPorTitulo(){
        DatosLibro datosLibro = obtenerDatosLibro();

        if(datosLibro != null){
            Libro libro;

            DatosAutor datosAutor = datosLibro.autor().get(0);

            Autor autorExistente = autorRepository.findByNombre(datosAutor.nombre());

            if (autorExistente != null){
                libro = new Libro(datosLibro, autorExistente);
            } else {
                Autor nuevoAutor = new Autor(datosAutor);
                libro = new Libro(datosLibro, nuevoAutor);
                autorRepository.save(nuevoAutor);
            }

            try {

                boolean estaElLibroRegistrado = estaElLibroRegistradoEnBd(libro.getTitulo());

                if (!estaElLibroRegistrado){

                    libroRepository.save(libro);
                    System.out.println(libro);

                } else {
                    System.out.println("El libro ya esta registrado en la Base de datos!");
                }
            } catch (Exception e){
                System.out.println("Algo ocurrio y no pudimos guardar el libro correctamente en la Dase de datos!");
            }
        } else {
            System.out.println("El libro no esta registrado en la API de Guntendex");
        }
    }

    private boolean estaElLibroRegistradoEnBd(String tituloLibro){
        Optional<Libro> libroBusquedaBd = libroRepository.findByTitulo(tituloLibro);

        if (libroBusquedaBd.isPresent()){
            return true;
        }

        return false;
    }

    private void mostrarListadoDeLibros(){
        List<Libro> libros = libroRepository.findAll();

        if (!libros.isEmpty()) {
            for (Libro libro : libros){
                System.out.println(libro.toString());
            }
        } else {
            System.out.println("No hay libros registrados en la base de datos!");
        }
    }

    private void mostrarListadoDeAutoresRegistrados(){
        List<Autor> autores = autorRepository.findAll();

        if (!autores.isEmpty()){
            for (Autor autor : autores){
                System.out.println(autor.toString());
            }
        } else {
            System.out.println("No hay autores registrados en la base de datos!");
        }
    }

    private void mostrarAutoresVivosEnUnAnio(Double anio){
        List<Autor> autoresVivos = autorRepository.obtenerAutoresVivosEnUnaAnio(anio);

        if (!autoresVivos.isEmpty()) {
            for (Autor autor : autoresVivos){
                System.out.println(autor.toString());
            }
        } else {
            System.out.println("No hay registros de autores de ese anio en la base de datos!");
        }
    }

    private void mostrarLibrosPorIdioma(String idioma){
        List<Libro> librosPorIdioma = libroRepository.findByIdiomas(idioma);

        if (!librosPorIdioma.isEmpty()){
            System.out.println("Mostrando libros por idioma: " + idioma);
            for (Libro libros : librosPorIdioma){
                System.out.println(libros.toString());
            }
        } else {
            System.out.println("No hay libros registrados con ese idioma");
        }
    }

    private void mostrarEstadisticasGeneralesDeLosLibros(){
        List<Libro> libros = libroRepository.findAll();

        if (!libros.isEmpty()) {
            DoubleSummaryStatistics est = libros.stream()
                    .filter(l -> l.getNumeroDeDescargas() > 0.0)
                    .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
            System.out.println("Estadisticas de los libros registrados en la base de datos: ");
            System.out.println("Libros registrados: " + est.getCount());
            System.out.println("Suma total de descargas: " + est.getSum());
            System.out.println("Descargas minimas: " + est.getMin());
            System.out.println("Descargas maximas: " + est.getMax());
            System.out.println("Promedio de descargas: " + est.getAverage());

        } else {
            System.out.println("No hay libros registrados en la base de datos!");
        }
    }

    private void mostrarAutorPorNombre(String nombre){
      Autor autor = autorRepository.findByNombrePersonalizado(nombre);

      if (autor != null){
          System.out.println(autor);
      } else {
          System.out.printf("Autor no encontrado!");
      }
    }

    private void top5Libros(){
        List<Libro> libros = libroRepository.findTop5ByDownloadNumber();
        for (Libro libro : libros){
            System.out.println(libro.toString());
        }
    }

    private DatosLibro obtenerDatosLibro(){
        System.out.println("Ingrese el nombre del libro: ");

        String nombreLibro = scanner.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));

        Datos datosBusqueda = convierteDatos.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(libro -> libro.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        return libroBuscado.orElse(null);
    }
}

