package sueldoempleado;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sueldoempleado.CalcularSalario;
import sueldoempleado.Empleado;
import sueldoempleado.EmpleadoDAO;

public class Interfaz {

    private VBox moduloOperaciones;
    private VBox moduloCalcularSalario;
    private VBox moduloEmpleado;

    private TextField horasTrabajadasField;
    private TextField pagoPorHoraBaseField;
    private TextField horasExtrasField;
    private TextField pagoPorHoraExtraField;
    private DatePicker fechaInicioPicker;
    private Label resultadoLabel;

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Salario de empleado");

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        crearModuloOperaciones();
        crearModuloCalcularSalario();
        crearModuloEmpleado();

        // Establecer visibilidad inicial
        moduloCalcularSalario.setVisible(false);
        moduloEmpleado.setVisible(false);

        // Crear un StackPane para contener los módulos "Calcular salario" y "Ver empleados"
        StackPane stackPane = new StackPane(moduloCalcularSalario, moduloEmpleado);

        // Colocar el StackPane en el centro del BorderPane
        borderPane.setCenter(stackPane);
        borderPane.setLeft(moduloOperaciones);

        Scene scene = new Scene(borderPane, 854, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void crearModuloOperaciones() {
        moduloOperaciones = new VBox();
        moduloOperaciones.setPrefWidth(150);
        moduloOperaciones.setPrefHeight(780);
        moduloOperaciones.setSpacing(10);
        moduloOperaciones.setStyle("-fx-background-color: lightgray;");
        Label operacionesLabel = new Label("Operaciones");
        operacionesLabel.setStyle("-fx-font-weight: bold; -fx-padding: 15px 0 0 20px;");
        moduloOperaciones.getChildren().add(operacionesLabel);

        ListView<String> operacionesList = new ListView<>();
        operacionesList.getItems().addAll("Calcular salario", "Empleados");
        moduloOperaciones.getChildren().add(operacionesList);

        // Acción para cambiar entre módulos
        operacionesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Calcular salario")) {
                moduloCalcularSalario.setVisible(true);
                moduloEmpleado.setVisible(false);
            } else if (newValue.equals("Empleados")) {
                moduloCalcularSalario.setVisible(false);
                moduloEmpleado.setVisible(true);
            }
        });
    }

    private void crearModuloCalcularSalario() {

        moduloCalcularSalario = new VBox();
        moduloCalcularSalario.setPrefWidth(700);
        moduloCalcularSalario.setPrefHeight(780);
        moduloCalcularSalario.setSpacing(10);
        moduloCalcularSalario.setStyle("-fx-background-color: lightblue;");
        Label calculadoraLabel = new Label("Calculadora");
        calculadoraLabel.setStyle("-fx-font-weight: bold; -fx-padding: 15px 0 0 20px;");
        moduloCalcularSalario.getChildren().add(calculadoraLabel);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Label horasTrabajadasLabel = new Label("Horas Trabajadas:");
        grid.add(horasTrabajadasLabel, 0, 0);
        horasTrabajadasField = new TextField();
        grid.add(horasTrabajadasField, 1, 0);
        Label pagoPorHoraBaseLabel = new Label("Pago por Hora Base:");
        grid.add(pagoPorHoraBaseLabel, 0, 1);
        pagoPorHoraBaseField = new TextField();
        grid.add(pagoPorHoraBaseField, 1, 1);
        Label horasExtrasLabel = new Label("Horas Extras Trabajadas:");
        grid.add(horasExtrasLabel, 0, 2);
        horasExtrasField = new TextField();
        grid.add(horasExtrasField, 1, 2);
        Label pagoPorHoraExtraLabel = new Label("Pago por Hora Extra:");
        grid.add(pagoPorHoraExtraLabel, 0, 3);
        pagoPorHoraExtraField = new TextField();
        grid.add(pagoPorHoraExtraField, 1, 3);
        Label fechaInicioLabel = new Label("Inicio del trabajo:");
        grid.add(fechaInicioLabel, 0, 4);
        fechaInicioPicker = new DatePicker();
        grid.add(fechaInicioPicker, 1, 4);
        Button calcularButton = new Button("Calcular");
        grid.add(calcularButton, 1, 5);
        resultadoLabel = new Label();
        grid.add(resultadoLabel, 1, 6);
        calcularButton.setOnAction(event -> calcularSalario());

        moduloCalcularSalario.getChildren().add(grid);
    }

    private void crearModuloEmpleado() {
        moduloEmpleado = new VBox();
        moduloEmpleado.setSpacing(10);
        moduloEmpleado.setStyle("-fx-background-color: lightgray;");

        // Crear la tabla para mostrar los empleados
        TableView<Empleado> tablaEmpleados = new TableView<>();

        // Definir las columnas de la tabla
        TableColumn<Empleado, Integer> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));

        TableColumn<Empleado, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Empleado, LocalDate> columnaFechaNacimiento = new TableColumn<>("Fecha de Nacimiento");
        columnaFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        TableColumn<Empleado, LocalDate> columnaFechaContratacion = new TableColumn<>("Fecha de Contratación");
        columnaFechaContratacion.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));

        TableColumn<Empleado, String> columnaTelefono = new TableColumn<>("Teléfono");
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Empleado, Integer> columnaIdCargo = new TableColumn<>("ID de Cargo");
        columnaIdCargo.setCellValueFactory(new PropertyValueFactory<>("idCargo"));

        TableColumn<Empleado, String> columnaCorreo = new TableColumn<>("Correo");
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        // Agregar las columnas a la tabla
        tablaEmpleados.getColumns().addAll(columnaId, columnaNombre, columnaFechaNacimiento,
                columnaFechaContratacion, columnaTelefono, columnaIdCargo, columnaCorreo);

        BD conexion = new BD();
        Connection tuConexion = conexion.getConexion();
        // Obtener todos los empleados al cargar el módulo
        EmpleadoDAO empleadoDAO = new EmpleadoDAO(tuConexion);
        tablaEmpleados.getItems().addAll(empleadoDAO.obtenerTodosLosEmpleados());

        // Agregar botones u otros controles según sea necesario
        Button agregarEmpleadoButton = new Button("Agregar Empleado");
        Button editarEmpleadoButton = new Button("Editar Empleado");
        Button eliminarEmpleadoButton = new Button("Eliminar Empleado");

        // Acción para el botón "Agregar Empleado"
        agregarEmpleadoButton.setOnAction(event -> {
            abrirVentanaAgregarEmpleado(empleadoDAO, tablaEmpleados);
        });

        // Acción para el botón "Editar Empleado"
        editarEmpleadoButton.setOnAction(event -> {
            // Aquí debes obtener el empleado seleccionado en la tabla
            Empleado empleadoSeleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
            if (empleadoSeleccionado != null) {
                abrirVentanaEditarEmpleado(empleadoDAO, tablaEmpleados, empleadoSeleccionado);
            } else {
                // Muestra un mensaje de error indicando que no se ha seleccionado ningún empleado
                mostrarMensajeError("Editar Empleado", "Por favor, selecciona un empleado para editar.");
            }
        });

        // Acción para el botón "Eliminar Empleado"
        eliminarEmpleadoButton.setOnAction(event -> {
            // Aquí debes obtener el empleado seleccionado en la tabla
            Empleado empleadoSeleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
            if (empleadoSeleccionado != null) {
                // Preguntas al usuario si está seguro de eliminar el empleado
                boolean confirmacion = mostrarConfirmacion("Eliminar Empleado", "¿Estás seguro de eliminar al empleado seleccionado?");
                if (confirmacion) {
                    // Si confirma, llamas al método eliminarEmpleado del EmpleadoDAO
                    empleadoDAO.eliminarEmpleado(empleadoSeleccionado);
                    // Y actualizas la tabla de empleados
                    tablaEmpleados.getItems().remove(empleadoSeleccionado);
                }
            } else {
                // Muestra un mensaje de error indicando que no se ha seleccionado ningún empleado
                mostrarMensajeError("Eliminar Empleado", "Por favor, selecciona un empleado para eliminar.");
            }
        });

        // Agregar los botones al VBox
        moduloEmpleado.getChildren().addAll(tablaEmpleados, agregarEmpleadoButton, editarEmpleadoButton, eliminarEmpleadoButton);

        // Establecer visibilidad inicial
        moduloEmpleado.setVisible(false);

        // Agregar el módulo de ver empleados al módulo principal
        moduloCalcularSalario.getChildren().add(moduloEmpleado);
    }

    // Método para mostrar un mensaje de error
    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar una confirmación
    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void calcularSalario() {
        try {
            double horasTrabajadas = Double.parseDouble(horasTrabajadasField.getText());
            double pagoPorHoraBase = Double.parseDouble(pagoPorHoraBaseField.getText());
            double horasExtras = Double.parseDouble(horasExtrasField.getText());
            double pagoPorHoraExtra = Double.parseDouble(pagoPorHoraExtraField.getText());
            LocalDate fechaInicio = fechaInicioPicker.getValue();
            double salarioTotal = CalcularSalario.calcularSalarioTotal(horasTrabajadas, pagoPorHoraBase, horasExtras, pagoPorHoraExtra, fechaInicio);
            resultadoLabel.setText("Salario Total: $" + salarioTotal);
        } catch (NumberFormatException e) {
            resultadoLabel.setText("Error: Asegúrate de ingresar números válidos.");
        } catch (NullPointerException e) {
            resultadoLabel.setText("Error: Asegúrate de ingresar una fecha de inicio.");
        }
    }

    private void abrirVentanaAgregarEmpleado(EmpleadoDAO empleadoDAO, TableView<Empleado> tablaEmpleados) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Agregar Empleado");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label idLabel = new Label("ID Empleado:");
        TextField idField = new TextField();

        Label nombreLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label fechaNacimientoLabel = new Label("Fecha de Nacimiento:");
        DatePicker fechaNacimientoPicker = new DatePicker();

        Label fechaContratacionLabel = new Label("Fecha de Contratación:");
        DatePicker fechaContratacionPicker = new DatePicker();

        Label telefonoLabel = new Label("Teléfono:");
        TextField telefonoField = new TextField();

        Label idCargoLabel = new Label("ID Cargo:");
        TextField idCargoField = new TextField();

        Label correoLabel = new Label("Correo:");
        TextField correoField = new TextField();

        Button agregarButton = new Button("Agregar");
        agregarButton.setOnAction(event -> {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
            LocalDate fechaContratacion = fechaContratacionPicker.getValue();
            String telefono = telefonoField.getText();
            int idCargo = Integer.parseInt(idCargoField.getText());
            String correo = correoField.getText();

            if (id != 0 && !nombre.isEmpty() && fechaNacimiento != null && fechaContratacion != null && !telefono.isEmpty() && idCargo != 0 && !correo.isEmpty()) {
                Empleado nuevoEmpleado = new Empleado(id, nombre, fechaNacimiento, fechaContratacion, telefono, idCargo, correo);
                empleadoDAO.agregarEmpleado(nuevoEmpleado);
                tablaEmpleados.getItems().add(nuevoEmpleado);
                ventana.close();
            }
        });

        layout.getChildren().addAll(
                idLabel, idField,
                nombreLabel, nombreField,
                fechaNacimientoLabel, fechaNacimientoPicker,
                fechaContratacionLabel, fechaContratacionPicker,
                telefonoLabel, telefonoField,
                idCargoLabel, idCargoField,
                correoLabel, correoField,
                agregarButton
        );
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        ventana.setScene(scene);
        ventana.showAndWait();
    }

    private void abrirVentanaEditarEmpleado(EmpleadoDAO empleadoDAO, TableView<Empleado> tablaEmpleados, Empleado empleadoSeleccionado) {
        if (empleadoSeleccionado != null) {
            Stage ventana = new Stage();
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setTitle("Editar Empleado");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));

            Label idLabel = new Label("ID Empleado:");
            TextField idField = new TextField(String.valueOf(empleadoSeleccionado.getIdEmpleado()));
            idField.setEditable(false);

            Label nombreLabel = new Label("Nombre:");
            TextField nombreField = new TextField(empleadoSeleccionado.getNombre());

            Label fechaNacimientoLabel = new Label("Fecha de Nacimiento:");
            DatePicker fechaNacimientoPicker = new DatePicker(empleadoSeleccionado.getFechaNacimiento());

            Label fechaContratacionLabel = new Label("Fecha de Contratación:");
            DatePicker fechaContratacionPicker = new DatePicker(empleadoSeleccionado.getFechaContratacion());

            Label telefonoLabel = new Label("Teléfono:");
            TextField telefonoField = new TextField(empleadoSeleccionado.getTelefono());

            Label idCargoLabel = new Label("ID Cargo:");
            TextField idCargoField = new TextField(String.valueOf(empleadoSeleccionado.getIdCargo()));

            Label correoLabel = new Label("Correo:");
            TextField correoField = new TextField(empleadoSeleccionado.getCorreo());

            Button guardarButton = new Button("Guardar");
            guardarButton.setOnAction(event -> {
                int nuevoId = Integer.parseInt(idField.getText());
                String nuevoNombre = nombreField.getText();
                LocalDate nuevaFechaNacimiento = fechaNacimientoPicker.getValue();
                LocalDate nuevaFechaContratacion = fechaContratacionPicker.getValue();
                String nuevoTelefono = telefonoField.getText();
                int nuevoIdCargo = Integer.parseInt(idCargoField.getText());
                String nuevoCorreo = correoField.getText();

                if (nuevoId != 0 && !nuevoNombre.isEmpty() && nuevaFechaNacimiento != null && nuevaFechaContratacion != null && !nuevoTelefono.isEmpty() && nuevoIdCargo != 0 && !nuevoCorreo.isEmpty()) {
                    empleadoSeleccionado.setIdEmpleado(nuevoId);
                    empleadoSeleccionado.setNombre(nuevoNombre);
                    empleadoSeleccionado.setFechaNacimiento(nuevaFechaNacimiento);
                    empleadoSeleccionado.setFechaContratacion(nuevaFechaContratacion);
                    empleadoSeleccionado.setTelefono(nuevoTelefono);
                    empleadoSeleccionado.setIdCargo(nuevoIdCargo);
                    empleadoSeleccionado.setCorreo(nuevoCorreo);
                    empleadoDAO.actualizarEmpleado(empleadoSeleccionado);
                    tablaEmpleados.refresh();
                    ventana.close();
                }
            });

            layout.getChildren().addAll(
                    idLabel, idField,
                    nombreLabel, nombreField,
                    fechaNacimientoLabel, fechaNacimientoPicker,
                    fechaContratacionLabel, fechaContratacionPicker,
                    telefonoLabel, telefonoField,
                    idCargoLabel, idCargoField,
                    correoLabel, correoField,
                    guardarButton
            );
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            ventana.setScene(scene);
            ventana.showAndWait();
        }
    }

}
