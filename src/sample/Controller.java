package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import sample.Objetos.Llibre;
import sample.Objetos.Prestec;
import sample.Objetos.Soci;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 46465442z on 24/01/16.
 */
public class Controller {

    // Nuevo objeto de nuestro DataAccesObject. Para hacer consultas a nuestra base de datos
    public DAO DAO = new DAO();

    public String dateError;        // Contiene un string que determina el tipo de error
    public String tipoModificar;    // Contiene un string que determina que es lo que vamos a modificar (libro o socio)
    public String tipoNuevo;        // Contiene un string que identifica que es lo que vamos a añadir (libro, prestamo, etc)
    public String tipoBusqueda;     // Contiene un string que identifica que es lo que vamos a buscar (libro, prestamo, etc)

    // Elementos de JAVA FX

    public ScrollPane scrollPane;   // ScrollPane
    public Text scrollText;         // Texto dentro del scrollPane
    public Text textoInfoSeccion;   // Texto con el nombre de la seccion
    public Text textoAyudaFechas;   // Texto con informacion para saber como introducir las fechas correctamente
    public TextField campoBusqueda; // Campo para buscar

    // Campos que reusaremos en cada seccion

    public TextField campoTexto1;   // Campo1
    public TextField campoTexto2;   // Campo2
    public TextField campoTexto3;   // Campo3
    public TextField campoTexto4;   // Campo4
    public TextField campoTexto5;   // Campo5
    public TextField campoTexto6;   // Campo6

    public Button buttonGuardar;    // Boton para guardar
    public Button buttonBuscar;     // Boton para buscar
    public Button buttonModificar;  // Boton para modificar

    public ListView listView;   // ListView para mostrar datos

    public ObservableList<String> observableLlibres = FXCollections.observableArrayList(); // ObservableList para nuestro listView
    public ObservableList<String> observableSocis = FXCollections.observableArrayList();   // ObservableList para nuestro listView
    public ObservableList<String> observablePrestec = FXCollections.observableArrayList(); // ObservableList para nuestro listView

    private ArrayList<Llibre> llibres = new ArrayList<>();      // Array de Libros
    private ArrayList<Soci> socis = new ArrayList<>();          // Array de Socios
    private ArrayList<Prestec> prestecs = new ArrayList<>();    // Array de prestamos

    // Metodos

    public void initialize() {
        descargarDatosBBDD();   // Con el primer metodo llenamos nuestro array con la información de la base de datos
    }

    public void descargarDatosBBDD() {

        try {
            llibres = DAO.obtenirLlibres();   // Llenamos nuestro array de libros con los de la BBDD

            observableLlibres.clear();

            for(int iterador = 0; iterador < llibres.size(); iterador++){
                observableLlibres.add(llibres.get(iterador).toString());
            }
        }
        catch (Exception noBooks){}

        try {
            socis = DAO.obtenirSocis();   // Llenamos nuestro array de socios con los de la BBDD

            observableSocis.clear();

            for(int iterador = 0; iterador < socis.size(); iterador++){
                observableLlibres.add(socis.get(iterador).toString());
            }
        }
        catch (Exception noMembers) {}

        try {
            observablePrestec.clear();

            prestecs = DAO.obtenirPrestecs();  // Llenamos nuestro array de socios con los de la BBDD

            for(int iterador = 0; iterador < prestecs.size(); iterador++){
                observablePrestec.add(prestecs.get(iterador).toString());
            }
        }
        catch (Exception noLoans){}
    }

    // Metodos para listar

    public void listaLlibres(ActionEvent actionEvent) {

        scrollText.setText("Llistat de llibres");   // Le asignamos el titulo
        scrollPane.setVisible(true);                    // Hacemos visible el scrollPane con el texto

        // Comprobamos nuestro array y mostramos lo que contiene
        if (llibres.size() == 0){
            listView.setItems(null);
            scrollText.setText(scrollText.getText() + "\n\n- No n'hi ha cap llibre");
        }
        else {
            listView.setItems(observableLlibres); // Le acoplamos el adaptador al listView
            listView.setVisible(true);            // hacemos visible el listView
        }

        // Para modificar cualquier libro se le hace clic encima
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tipoModificar = "libro";
                int idListView = listView.getSelectionModel().getSelectedIndex();
                mostrarCamposModificar(idListView);
            }
        });
    }

    public void listaSocis(ActionEvent actionEvent) {

        scrollText.setText("Llistat de socis");    // Le asignamos el titulo
        scrollPane.setVisible(true);               // Hacemos visible el scrollPane con el texto

        // Comprobamos nuestro array y mostramos lo que contiene
        if (socis.size() == 0){
            listView.setItems(null);
            scrollText.setText(scrollText.getText() + "\n\n- No n'hi ha cap soci");
        }
        else {
            listView.setItems(observableSocis);   // Le acoplamos el adaptador al listView
            listView.setVisible(true);            // hacemos visible el listView
        }

        // Para modificar cualquier socio se le hace clic encima
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tipoModificar = "soci";
                int idListView = listView.getSelectionModel().getSelectedIndex();
                mostrarCamposModificar(idListView);
            }
        });
    }

    public void listaPrestecs(ActionEvent actionEvent) {

        scrollText.setText("Llista de prestecs");    // Le asignamos el titulo
        scrollPane.setVisible(true);                 // Hacemos visible el scrollPane con el texto

        // Comprobamos nuestro array y mostramos lo que contiene
        if (prestecs.size() == 0){
            listView.setItems(null);
            scrollText.setText(scrollText.getText() + "\n\n- No n'hi ha cap prestec");
        }
        else {
            listView.setItems(observablePrestec); // Le acoplamos el adaptador al listView
            listView.setVisible(true);            // hacemos visible el listView
        }
    }

    public void listaLibrosFueraPlazo(ActionEvent actionEvent) {

        scrollText.setText("\n   Llista de llibres fora de temps");
        ObservableList<String> librosFueraPlazo = FXCollections.observableArrayList(); // ObservableList para nuestro listView
        scrollPane.setVisible(true);

        Date today = new Date();
        for (int iterador = 0; iterador < prestecs.size(); iterador++) {
            if (today.equals(prestecs.get(iterador).getDataFinal()) || today.after(prestecs.get(iterador).getDataFinal())) {
                librosFueraPlazo.add(prestecs.get(iterador).getLlibre().toString() +
                        "\n     Data Actual: " + new Date().toString() +
                        "\n     Data Limit: " + prestecs.get(iterador).getDataFinal().toString());
            }
        }

        // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
        if(librosFueraPlazo.size() == 0){
            listView.setItems(null);
            scrollText.setText("    No n'hi han resultats");
        }
        else {
            listView.setItems(librosFueraPlazo);
            listView.setVisible(true);
        }
    }

    public void listaSociosFueraPlazo(ActionEvent actionEvent) {

        scrollText.setText("\n   Llista de socis amb llibres fora de temps");
        ObservableList<String> sociosFueraPlazo = FXCollections.observableArrayList(); // ObservableList para nuestro listView
        scrollPane.setVisible(true);

        Date today = new Date();
        for (int iterador = 0; iterador < prestecs.size(); iterador++) {
            if (today.equals(prestecs.get(iterador).getDataFinal()) || today.after(prestecs.get(iterador).getDataFinal())) {
                sociosFueraPlazo.add(prestecs.get(iterador).getSoci().toString() +
                        "\n     Data Actual: " + new Date().toString() +
                        "\n     Data Limit: " + prestecs.get(iterador).getDataFinal().toString());
            }
        }

        // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
        if(sociosFueraPlazo.size() == 0){
            listView.setItems(null);
            scrollText.setText("    No n'hi han resultats");
        }
        else {
            listView.setItems(sociosFueraPlazo);
            listView.setVisible(true);
        }
    }

    // Metodos para anyadir datos a la BBDD

    private void afegirLlibre(){

        // Comprobamos si se han rellenado todos los campos
        if (campoTexto1.getText().equals("") || campoTexto2.getText().equals("") || campoTexto3.getText().equals("") || campoTexto4.getText().equals("") || campoTexto5.getText().equals("") || campoTexto6.getText().equals("")) {
            textoInfoSeccion.setText("\nS'han d'omplir correctament tots els camps.");
        }
        else {
            // Creamos un nuevo libro
            Llibre llibre = new Llibre();

            ocultarTodo();      // Ocultamos todos los campos para que no puedan haber modificaciones a la hora de anyadir

            // Y le asignamos su información
            llibre.setTitol(campoTexto1.getText());           // Extaemos el texto del campo y le asignamos titulo
            llibre.setNombreExemplars(campoTexto2.getText()); // Extaemos el texto del campo y le asignamos el numero de ejemplares
            llibre.setEditorial(campoTexto3.getText());       // Extaemos el texto del campo y le asignamos editorial
            llibre.setNombrePagines(campoTexto4.getText());   // Extaemos el texto del campo y le asignamos numero de paginas
            llibre.setAnyEdicio(campoTexto5.getText());       // Extaemos el texto del campo y le asignamos el anyo de edicion
            llibre.setAutor(campoTexto6.getText());           // Extaemos el texto del campo y le asignamos autor

            observableLlibres.add(llibre.toString());   // Anyadimos la informacion del libro a nuestro observableList
            llibres.add(llibre);                        // Anyadimos el libro a nuestro ArrayList
            DAO.afegirLlibre(llibre);                   // Anyadimos el libro a nuestra BBDD

            ocultarTodo();
            textoInfoSeccion.setVisible(true);          // Hacemos visible el texto informativo
            textoInfoSeccion.setText("\nS'ha creat correctament el llibre: \n" + llibre.toString());

        }
    }

    private void afegirSoci() {

        // Comprobamos si se han rellenado todos los campos
        if (campoTexto1.getText().equals("") || campoTexto2.getText().equals("") ||campoTexto3.getText().equals("") ||campoTexto4.getText().equals("") ||campoTexto5.getText().equals("")) {
            textoInfoSeccion.setText("\nS'han d'omplir correctament tots els camps.");
        }
        else {
            // Creamos un nuevo socio
            Soci soci = new Soci();

            ocultarTodo();      // Ocultamos todos los campos para que no puedan haber modificaciones a la hora de anyadir

            // Y le asignamos su información

            soci.setNom(campoTexto1.getText());          // Extaemos el texto del campo y le asignamos nombre
            soci.setCognom(campoTexto2.getText());       // Extaemos el texto del campo y le asignamos apellidos
            soci.setEdat(campoTexto3.getText());         // Extaemos el texto del campo y le asignamos la edad
            soci.setDireccio(campoTexto4.getText());     // Extaemos el texto del campo y le asignamos una direccion
            soci.setTelefon(campoTexto5.getText());      // Extaemos el texto del campo y le asignamos un telefono de contacto

            observableSocis.add(soci.toString());   // Anyadimos la informacion del socio a nuestro observableList
            socis.add(soci);                        // Anyadimos el libro a nuestro ArrayList
            DAO.afegirSoci(soci);                   // Anyadimos el libro a nuestra BBDD

            ocultarTodo();
            textoInfoSeccion.setVisible(true);      // Hacemos visible el texto informativo
            textoInfoSeccion.setText("\nS'ha creat correctament el soci: \n" + soci.toString());
        }
    }

    private void afegirPrestec() {

        // Comprobamos si se han rellenado todos los campos
        if (campoTexto1.getText().equals("") || campoTexto2.getText().equals("") ||campoTexto3.getText().equals("") ||campoTexto4.getText().equals("")) {
            textoInfoSeccion.setText("\nS'han d'omplir correctament tots els camps.");
        }
        else {
            // Creamos un nuevo prestamo
            Prestec prestec = new Prestec();

            // Buscamos el libro introducido en nuestro array de libros y se lo asignamos al prestamo
            for (int iterador = 0; iterador < llibres.size(); iterador++) {
                if (llibres.get(iterador).getTitol().toLowerCase().equals(campoTexto1.getText().toLowerCase())) {
                    prestec.setLlibre(llibres.get(iterador));
                }
            }

            // Buscamos el socio introducido en nuestro array de socios y se lo asignamos al prestamo
            for (int iterador = 0; iterador < socis.size(); iterador++) {
                if (socis.get(iterador).getNom().toLowerCase().equals(campoTexto2.getText().toLowerCase())) {
                    prestec.setSoci(socis.get(iterador));
                }
            }

            try  {
                DateFormat formatData = new SimpleDateFormat("mm/dd/yyyy");

                // Extraemos las fechas de nuestro campo de texto y le damos el formato correcto
                Date dataInici = formatData.parse(campoTexto3.getText());
                Date dataFinal = formatData.parse(campoTexto4.getText());

                ocultarTodo();       // Primero escondemos todos los objetos

                prestec.setDataInici(dataInici);    // Le damos la fecha de inicio del prestamo
                prestec.setDataFinal(dataFinal);    // Aqui le da mos la fecha de finalizacion

                // Y se comparan las fechas

                if(dataInici.equals(dataFinal)) {
                    dateError = "Fechas Iguales";
                    throw new InvalidDateException();
                }

                if (dataInici.after(dataFinal)) {
                    dateError = "Fechas Incorrectas";
                    throw new InvalidDateException();
                }

                try  {
                    textoInfoSeccion.setText("\nCREAT PRESTEC: \n" + prestec.toString());
                    textoInfoSeccion.setVisible(true);    // Hacemos visible nuestro texto

                    observablePrestec.add(prestec.toString());   // Anyadimos la informacion del prestamo a nuestro observableList
                    prestecs.add(prestec);                       // anyadimos el prestamo a nuestro arrayList
                    DAO.afegirPrestec(prestec);                 // anyadimos el prestamo a la BBDD

                } catch (Exception one) {
                    textoInfoSeccion.setText("\nLa informacio cercada no esta disponible");
                }
            }
            catch(InvalidDateException two) {

                if (dateError.equals("Fechas Iguales"))  {
                    textoAyudaFechas.setText("\n ERROR: Les dates introduides son iguals.");
                }

                if (dateError.equals("Fechas Incorrectas")) {
                    textoAyudaFechas.setText("\n La data de fi es anterior a la de inici");
                }
            }
            catch (Exception three) {
                textoInfoSeccion.setText("\n El format de les dates és incorrecte. Recorda que ha de seguir el estil \"DD/MM/YYYY\"\"");
            }
        }
    }

    // Metodos para mostrar distintos campos y secciones de la interfaz

    public void crearLlibre(ActionEvent actionEvent) {

        tipoNuevo = "libro";    // Asignamos a la variable que lo que vamos a crear es un libro

        textoInfoSeccion.setText("\nIntrodueix les dades del nou llibre");
        campoTexto1.setPromptText("Titol");              // Le damos el texto provisional al campoTexto1
        campoTexto2.setPromptText("Num Exemplars");      // Le damos el texto provisional al campoTexto2
        campoTexto3.setPromptText("Editorial");          // Le damos el texto provisional al campoTexto3
        campoTexto4.setPromptText("Num Pagines");        // Le damos el texto provisional al campoTexto4
        campoTexto5.setPromptText("Any Edició");         // Le damos el texto provisional al campoTexto5
        campoTexto6.setPromptText("Autor");              // Le damos el texto provisional al campoTexto6

        mostarCamposCrear(true);    // Mostramos todos los campos correspondientes a crear un libro
    }

    public void crearSoci(ActionEvent actionEvent)  {

        tipoNuevo = "soci";    // Asignamos a la variable que lo que vamos a crear es un socio

        textoInfoSeccion.setText("\nIntrodueix les dades del nou soci");
        campoTexto1.setPromptText("Nom");              // Le damos el texto provisional al campoTexto1
        campoTexto2.setPromptText("Cognom");           // Le damos el texto provisional al campoTexto2
        campoTexto3.setPromptText("Edat");             // Le damos el texto provisional al campoTexto3
        campoTexto4.setPromptText("Direccio");         // Le damos el texto provisional al campoTexto4
        campoTexto5.setPromptText("Telefon");          // Le damos el texto provisional al campoTexto5

        mostarCamposCrear(true);    // Mostramos todos los campos correspondientes a crear un socio
    }

    public void crearPrestec(ActionEvent actionEvent) {

        tipoNuevo = "prestec";     // Asignamos a la variable que lo que vamos a crear es un prestamo

        textoInfoSeccion.setText("\nIntrodueix les dades del nou prestec");
        campoTexto1.setPromptText("Titol Llibre");                                              // Le damos el texto provisional al campoTexto1
        campoTexto2.setPromptText("Nom Soci");                                                  // Le damos el texto provisional al campoTexto2
        campoTexto3.setPromptText("Data Inici DD/MM/YYYY");                                     // Le damos el texto provisional al campoTexto3
        campoTexto4.setPromptText("Data Final DD/MM/YYYY");                                      // Le damos el texto provisional al campoTexto4
        textoAyudaFechas.setText("\nEl format de les Dates ha de ser   \"  DD/MM/YYYY  \"");    // Texto de advertencia para las fechas

        mostarCamposCrear(true);            // Mostramos todos los campos correspondientes a crear un prestamo
        textoAyudaFechas.setVisible(true);  // Y en este caso tambien mostramos el texto de ayuda para las fechas
    }

    public void guardarDatos(ActionEvent actionEvent){

        // Depende del valor de la variable, se guardaran los campos de crear un libro, un socio o un prestamo.

        if (tipoNuevo.equals("libro")) {
            afegirLlibre();
            ocultarTodo();
            listaLlibres(null);
        }
        else if (tipoNuevo.equals("soci")) {
            ocultarTodo();
            afegirSoci();
            listaSocis(null);
        }
        else if (tipoNuevo.equals("prestec")) {
            afegirPrestec();
            ocultarTodo();
            listaPrestecs(null);
        }
    }

    public void buscarLibroPorTitulo(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorTitulo"; // le damos el valor correspondiente a la varible de tipo de busqueda

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\nBuscar llibres per titol.");
        campoBusqueda.setPromptText("Titol");

        mostrarCamposBusqueda();            // Mostramos los campos necesarios para buscar
        textoInfoSeccion.setVisible(true);  // Mostramos el texto auxiliar
    }

    public void buscarLibroPorAutor(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorAutor";  // le damos el valor correspondiente a la varible de tipo de busqueda

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\nBuscar llibres per autor.");
        campoBusqueda.setPromptText("Autor");

        mostrarCamposBusqueda();            // Mostramos los campos necesarios para buscar
        textoInfoSeccion.setVisible(true);  // Mostramos el texto auxiliar
    }

    public void buscarSocioPorNombre(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorNombre"; // le damos el valor correspondiente a la varible de tipo de busqueda

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\nBuscar socis per nom");
        campoBusqueda.setPromptText("Nom");

        mostrarCamposBusqueda();            // Mostramos los campos necesarios para buscar
        textoInfoSeccion.setVisible(true);  // Mostramos el texto auxiliar
    }

    public void buscarSocioPorApellido(ActionEvent actionEvent) {

        tipoBusqueda = "busquedaPorApellido";   // le damos el valor correspondiente a la varible de tipo de busqueda

        // Establecemos el texto del campo y el titulo
        textoInfoSeccion.setText("\nBuscar socis per cognom.");
        campoBusqueda.setPromptText("Cognom");

        mostrarCamposBusqueda();            // Mostramos los campos necesarios para buscar
        textoInfoSeccion.setVisible(true);  // Mostramos el texto auxiliar
    }

    // Buttons

    public void buscar(ActionEvent actionEvent) {

        if (tipoBusqueda.equals("busquedaPorTitulo")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultats per a la cerca de llibres amb el titol '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaTitulo = FXCollections.observableArrayList(); // ObservableList para nuestro listView
            scrollPane.setVisible(true);

            for (int iterador = 0; iterador < llibres.size(); iterador++) {
                if (llibres.get(iterador).getTitol().toLowerCase().equals(campoBusqueda.getText().toLowerCase())) {
                    busquedaTitulo.add(llibres.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaTitulo.size() == 0){
                scrollText.setText("  No n'hi han resultats");
            }
            else {
                listView.setItems(busquedaTitulo);
                listView.setVisible(true);
            }
        }
        else if (tipoBusqueda.equals("busquedaPorAutor")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultat per a la cerca de llibres amb el autor:   '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaAutor = FXCollections.observableArrayList(); // ObservableList para nuestro listView
            scrollPane.setVisible(true);

            // Recorremos el array buscando libros en el que el autor coincida
            for (int iterador = 0; iterador < llibres.size(); iterador++) {
                if (llibres.get(iterador).getAutor().toLowerCase().equals(campoBusqueda.getText().toLowerCase())) {
                    busquedaAutor.add(llibres.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaAutor.size() == 0){
                scrollText.setText("  No n'hi han resultats");
            }
            else {
                listView.setItems(busquedaAutor);
                listView.setVisible(true);
            }
        }
        else if (tipoBusqueda.equals("busquedaPorNombre")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultats de socis amb el nom:    '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaNombre = FXCollections.observableArrayList(); // ObservableList para nuestro listView
            scrollPane.setVisible(true);

            for (int iterador = 0; iterador < socis.size(); iterador++) {
                if (socis.get(iterador).getNom().toLowerCase().equals(campoBusqueda.getText().toLowerCase())) {
                    busquedaNombre.add(socis.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaNombre.size() == 0){
                scrollText.setText("  No n'hi han resultats");
            }
            else {
                listView.setItems(busquedaNombre);
                listView.setVisible(true);
            }
        }
        else if (tipoBusqueda.equals("busquedaPorApellido")) {

            // Damos el texto correspondiente, hacemos el observable para guardar los resultados de la busqueda y mostramos la seccion
            scrollText.setText("\n  Resultats de socis amb el cognoom:    '" + campoBusqueda.getText().toLowerCase() + "'");
            ObservableList<String> busquedaApellidos = FXCollections.observableArrayList(); // ObservableList para nuestro listView
            scrollPane.setVisible(true);

            for (int iterador = 0; iterador < socis.size(); iterador++) {
                if (socis.get(iterador).getCognom().toLowerCase().equals(campoBusqueda.getText().toLowerCase())){
                    busquedaApellidos.add(socis.get(iterador).toString());
                }
            }

            // Miramos qué hemos encontrado y segun si hay reusltados mostramos una cosa u otra
            if(busquedaApellidos.size() == 0){
                scrollText.setText("  No n'hi han resultats");
            }
            else {
                listView.setItems(busquedaApellidos);
                listView.setVisible(true);
            }
        }
    }

    public void modificar(ActionEvent actionEvent) {

        if (tipoModificar.equals("libro")) {

            int idSelected = listView.getSelectionModel().getSelectedIndex();

            Llibre llibre = new Llibre();

            // Y le asignamos su información
            llibre.setTitol(campoTexto1.getText());           // Extaemos el texto del campo y le asignamos titulo
            llibre.setNombreExemplars(campoTexto2.getText()); // Extaemos el texto del campo y le asignamos el numero de ejemplares
            llibre.setEditorial(campoTexto3.getText());       // Extaemos el texto del campo y le asignamos editorial
            llibre.setNombrePagines(campoTexto4.getText());   // Extaemos el texto del campo y le asignamos numero de paginas
            llibre.setAnyEdicio(campoTexto5.getText());       // Extaemos el texto del campo y le asignamos el anyo de edicion
            llibre.setAutor(campoTexto6.getText());           // Extaemos el texto del campo y le asignamos autor

            llibres.set(idSelected, llibre);
            observableLlibres.set(idSelected, llibre.toString());
            // DAO.afegirLlibre(llibre);                               // Anyadimos el libro a nuestra BBDD

            ocultarTodo();
            listaLlibres(null);
        }
        else if(tipoModificar.equals("soci")){

            int idSelected = listView.getSelectionModel().getSelectedIndex();

            Soci soci = new Soci();

            // Y le asignamos su información
            soci.setNom(campoTexto1.getText());          // Extaemos el texto del campo y le asignamos nombre
            soci.setCognom(campoTexto2.getText());       // Extaemos el texto del campo y le asignamos apellidos
            soci.setEdat(campoTexto3.getText());         // Extaemos el texto del campo y le asignamos la edad
            soci.setDireccio(campoTexto4.getText());     // Extaemos el texto del campo y le asignamos una direccion
            soci.setTelefon(campoTexto5.getText());      // Extaemos el texto del campo y le asignamos un telefono de contacto

            socis.set(idSelected, soci);
            observableSocis.set(idSelected, soci.toString());
            DAO.afegirSoci(soci);                               // Anyadimos el socio a nuestra BBDD

            ocultarTodo();
            listaLlibres(null);
        }
    }

    // Métodos para mostrar campos y mostrar la interfaz

    public void mostrarCamposModificar(int idListView){

       // El id del list view será igual a la posición en el arrayList del item seleccionado

        if(tipoModificar.equals("libro")){  // Si lo que queremos modificar es un libro
            tipoNuevo = "libro";

            // Seteamos todos los campos con los datos del libro que queremos modificar
            campoTexto1.setText(llibres.get(idListView).getTitol());
            campoTexto2.setText(llibres.get(idListView).getNombreExemplars());
            campoTexto3.setText(llibres.get(idListView).getEditorial());
            campoTexto4.setText(llibres.get(idListView).getNombrePagines());
            campoTexto5.setText(llibres.get(idListView).getAnyEdicio());
            campoTexto6.setText(llibres.get(idListView).getAutor());

            ocultarTodo();                    // Ocultamos todo
            mostarCamposCrear(false);         // Y mostramos los campos

            // La interfaz de modificar será igual a la de crear per con el botón distinto
            buttonGuardar.setVisible(false);  // Ocultamos el botón de guardar
            buttonModificar.setVisible(true); // mostramos el de modificar
            buttonModificar.requestFocus();
        }
        else{
            tipoNuevo = "soci"; // Si lo que queremos modificar es un socio

            // Seteamos todos los campos con los datos del socio que queremos modificar
            campoTexto1.setText(socis.get(idListView).getNom());
            campoTexto2.setText(socis.get(idListView).getCognom());
            campoTexto3.setText(socis.get(idListView).getEdat());
            campoTexto4.setText(socis.get(idListView).getDireccio());
            campoTexto5.setText(socis.get(idListView).getEdat());

            ocultarTodo();              // Ocultamos todo
            mostarCamposCrear(false);   // Y mostramos los campos

            // La interfaz de modificar será igual a la de crear per con el botón distinto
            buttonGuardar.setVisible(false);  // Ocultamos el botón de guardar
            buttonModificar.setVisible(true); // mostramos el de modificar
            buttonModificar.requestFocus();
        }
    }

    public void mostarCamposCrear(boolean limpiarCampos) {

        // Si el booleano limpiar campos es true, antes de mostrarlos, limpiaremos los fields

        ocultarTodo();   // Ocultamos primero todos los elementos

        buttonGuardar.requestFocus();

        if(limpiarCampos == true){
            // Limpiamos los campos
            campoTexto1.clear();
            campoTexto2.clear();
            campoTexto3.clear();
            campoTexto4.clear();
        }

        // Hacemos visible los elementos

        campoTexto1.setVisible(true);       // Mostramos el campo de texto 1
        campoTexto2.setVisible(true);       // Mostramos el campo de texto 2
        campoTexto3.setVisible(true);       // Mostramos el campo de texto 3
        campoTexto4.setVisible(true);       // Mostramos el campo de texto 4
        scrollPane.setVisible(false);       // Mostramos el scrollPane
        buttonGuardar.setVisible(true);     // Mostramos el boton de guardar
        textoInfoSeccion.setVisible(true);  // Mostramos el texto informativo

        // Según lo que estemos mostrando será necesario enseñar un campo u otro

        if (tipoNuevo.equals("soci")) {
            if(limpiarCampos == true){
                campoTexto5.clear();         // Limpiamos el texto del campo
            }
            campoTexto5.setVisible(true);    // Mostramos el campo de texto 5
        }
        else if (tipoNuevo.equals("libro")) {
            if(limpiarCampos == true){
                campoTexto5.clear();         // Limpiamos el texto del campo
                campoTexto6.clear();         // Limpiamos el texto del campos
            }

            campoTexto5.setVisible(true);    // Mostramos el campo de texto 5
            campoTexto6.setVisible(true);    // Mostramos el campo de texto 6
        }
    }

    public void mostrarCamposBusqueda() {

        ocultarTodo();                      // Ocultamos primero todos los elementos

        campoBusqueda.clear();              // Limpiamos el campo de busqueda por si quedaba texto de antes
        campoBusqueda.setVisible(true);     // Mostramos el campo de busqueda
        buttonBuscar.setVisible(true);      // Hacemos visible el botón de buscar
        buttonBuscar.requestFocus();        // Lo ponemos en el focus
    }

    public void ocultarTodo() {

        scrollPane.setVisible(false);           // Ocultamos el scrollPane
        textoInfoSeccion.setVisible(false);     // Ocultamos el texto que informa de la sección en la que nos encontramos
        campoBusqueda.setVisible(false);        // Ocultamos el campo de busqueda
        campoTexto1.setVisible(false);          // Ocultamos el campo de texto 1
        campoTexto2.setVisible(false);          // Ocultamos el campo de texto 2
        campoTexto3.setVisible(false);          // Ocultamos el campo de texto 3
        campoTexto4.setVisible(false);          // Ocultamos el campo de texto 4
        campoTexto5.setVisible(false);          // Ocultamos el campo de texto 5
        campoTexto6.setVisible(false);          // Ocultamos el campo de texto 6
        buttonBuscar.setVisible(false);         // Ocultamos el botón de buscar
        buttonGuardar.setVisible(false);        // Ocultamos el botón de guardar
        buttonModificar.setVisible(false);      // Ocultamos el botón de modificar
        textoAyudaFechas.setVisible(false);     // Ocultamos el texto de ayuda para mostrar la fecha
        listView.setVisible(false);             // Ocultamos el textView
    }

    // Otros metodos

    public void close(ActionEvent actionEvent) {    // Cierra la APP
        Platform.exit();
    }

    public void deleteAll(ActionEvent actionEvent) {

        if (DAO.eliminarTotsElsLlibres()) {     // Si ha sido posible eliminar los libros de la BBDD vacía tambien nuestro arrayList
            llibres.clear();
        }
        if (DAO.eliminarTotsElsSocis()) {       // Si ha sido posible eliminar los socios de la BBDD vacía tambien nuestro arrayList
            socis.clear();
        }
        if (DAO.eliminarTotsElsPrestecs()){     // Si ha sido posible eliminar los prestamos de la BBDD vacía tambien nuestro arrayList
            prestecs.clear();
        }
    }

    public void about(ActionEvent actionEvent) {      // Dialogo de informacion sobre la APP

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Biblioteca v0.1");
        alert.setHeaderText("Biblioteca v0.1");
        alert.setContentText(" Benvingut a la Biblioteca virtual." +
                "\nPodras modificar, afegir i cercar llibres, socis i prestecs " +
                "\nDeveloped by Sergi Barjola. Project aviable on gitHub:" +
                "\nhttps://github.com/helicida/Biblioteca" +
                "\n               " +
                "\n               ");

        alert.showAndWait();
    }


    // Clase auxiliar para gestionar las excepciones
    public class InvalidDateException extends Exception {
        public InvalidDateException(){

        }
    }
}


