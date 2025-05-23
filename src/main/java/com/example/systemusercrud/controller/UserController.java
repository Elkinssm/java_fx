package com.example.systemusercrud.controller;

import com.example.systemusercrud.model.User;
import com.example.systemusercrud.model.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

public class UserController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField roleField;
    @FXML private TextField statusField;

    @FXML private Label nameError;
    @FXML private Label emailError;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> roleColumn;
    @FXML private TableColumn<User, String> statusColumn;

    private final UserDAO userDAO = new UserDAO();
    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadUsers();

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) fillForm(newVal);
        });
    }

    private void loadUsers() {
        userList.setAll(userDAO.getAllUsers());
        userTable.setItems(userList);
    }

    private void fillForm(User user) {
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        roleField.setText(user.getRole());
        statusField.setText(user.getStatus());
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        roleField.clear();
        statusField.clear();
        userTable.getSelectionModel().clearSelection();
        nameError.setText("");
        emailError.setText("");
    }

    private boolean isValidForm() {
        boolean valid = true;
        nameError.setText("");
        emailError.setText("");

        if (nameField.getText().isBlank()) {
            nameError.setText("Nombre requerido");
            valid = false;
        }

        if (!emailField.getText().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            emailError.setText("Correo inválido");
            valid = false;
        }

        return valid;
    }

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    @FXML
    private void handleAddUser() {
        if (!isValidForm()) return;

        User user = new User(0,
                nameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                roleField.getText(),
                statusField.getText());

        userDAO.addUser(user);
        loadUsers();
        clearForm();
        showNotification("Usuario agregado", "El usuario fue registrado correctamente.");
    }

    @FXML
    private void handleUpdateUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!isValidForm()) return;

        selected.setName(nameField.getText());
        selected.setEmail(emailField.getText());
        selected.setPassword(passwordField.getText());
        selected.setRole(roleField.getText());
        selected.setStatus(statusField.getText());

        userDAO.updateUser(selected);
        loadUsers();
        clearForm();
        showNotification("Usuario actualizado", "Los datos fueron actualizados.");
    }

    @FXML
    private void handleDeleteUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        userDAO.deleteUser(selected.getId());
        loadUsers();
        clearForm();
        showNotification("Usuario eliminado", "El usuario fue eliminado.");
    }
}
