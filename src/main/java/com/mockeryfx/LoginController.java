package com.mockeryfx;

import com.model.MockeryFacade;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (MockeryFacade.getInstance().login(username, password)) {
            try {
                App.setRoot("dashboard");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    private void goToRegister() {
        try {
            App.setRoot("register");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
