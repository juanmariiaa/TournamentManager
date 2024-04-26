package org.juanmariiaa.utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.security.MessageDigest;

public class Utils {
<<<<<<< HEAD

     public static Alert showPopUp(String title, String header, String text, Alert.AlertType type) {
=======
    public static Alert showPopUp(String title, String header, String text, Alert.AlertType type) {
>>>>>>> origin
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
    public static String encryptSHA256 (String s){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");

            md.update(s.getBytes()); 	// Se actualiza el MessageDigest con los bytes de la cadena de entrada

            StringBuilder sb = new StringBuilder();
            for (byte aByte : md.digest()) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;  // Se devuelve el resultado del hash en formato hexadecimal
    }
<<<<<<< HEAD

}
=======
}
>>>>>>> origin
