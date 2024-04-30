package org.juanmariiaa.utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.security.MessageDigest;

public class Utils {

    public static Alert showPopUp(String title, String header, String text, Alert.AlertType type) {
        Alert alertDialog = new Alert(type);
        alertDialog.setTitle(title);
        alertDialog.setHeaderText(header);
        alertDialog.setContentText(text);

        // Mostrar el cuadro de diálogo de manera no bloqueante
        alertDialog.show();

        Stage s = (Stage) alertDialog.getDialogPane().getScene().getWindow();
        s.toFront();

        return alertDialog;
    }
}

