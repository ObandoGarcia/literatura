<h1>APLICACION DE ADMINISTRACION DE LIBROS DESDE LA API DE GUTENDEX</h1>
<h4>Creada por Jose Manuel Obando Garcia</h4>
<br>
<h2>Principales caracteristicas</h2>
<h3>OPTENER DATOS DE LA API DE GUTENDEX</h3>
<ol>
  <li>Uso de link publico de Gutendex para obtener el listado de libros</li>
  <li>https://gutendex.com/books/</li>
</ol>
<h3>HERRAMIENTAS Y PROGRAMAS</h3>
<ol>
  <li>Postgres: base de datos para guradar los libros conultados</li>
  <li>Spring Boot 3</li>
  <li>Spring Data JPA</li>
  <li>Jackson</li>
  <li>Intellij IDEA</li>
  <li>GitHub</li>
</ol>
<h3>ESTRUCTURA DEL PROYECTO</h3>
<ol>
  <li>Uso de DTO para convertir los datos del JSON</li>
  <li>Uso de modelos de objetos JAVA</li>
  <li>Uso de repositorios para acceder a los datos de la Base de datos</li>
  <li>Uso de clases services para convertir los datos de la respuesta de GUTENDEX</li>
</ol>
<h3>FUNCIONES DE LA APLICACION</h3>
<ol>
  <li>Buscar un libro por titulo validando si ya existe el autor y si el libro ya esta registrado en la base de datos</li>
  <li>Listar los libros registrados en la base de datos</li>
  <li>Listar los autores registrados</li>
  <li>Listar autores vivos en un determinado anio</li>
  <li>Listar libros por idiomas</li>
  <li>Mostrar estaditicas de los libros</li>
  <li>Buscar autores por nombre</li>
  <li>Mostrar TOP 5 de libros mas descargados(ya registrados en la base de datos)</li>
</ol>
