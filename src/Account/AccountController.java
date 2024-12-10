/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author omar-so
 */
public class AccountController implements Initializable {

    @FXML
    TextField currentUsernameField, newUsernameField;
    @FXML
    TextField currentPasswordField, newPasswordField;

    @FXML
    public void changebutt(ActionEvent ee) {

        String currn = currentUsernameField.getText(), currp = currentPasswordField.getText();
        String newn = newUsernameField.getText(), newp = newPasswordField.getText();

        String path = "/home/omar-so/NetBeansProjects/Main/src/signup/admin.txt";
        String tempPath = "/home/omar-so/NetBeansProjects/Main/src/signup/admin_temp.txt";
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(path)); FileWriter fw = new FileWriter(tempPath)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0].equals(currn) && data[1].equals(currp)) {
                    fw.write(newn + " " + newp + "\n");
                    found = true;
                } else {
                    fw.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            java.nio.file.Files.delete(java.nio.file.Paths.get(path));
            java.nio.file.Files.move(java.nio.file.Paths.get(tempPath), java.nio.file.Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (found) {
            showSuccess("You Changed it Successfully");
        } else {
            showError("Wrong Password or Email");
        }
    }

    @FXML
    public void reserbutt(ActionEvent e) {
        currentPasswordField.setText("");
        currentUsernameField.setText("");
        newPasswordField.setText("");
        newUsernameField.setText("");
    }

    @FXML
    public void Logout(ActionEvent e) throws IOException {
        // Load a new scene when the button is clicked
        Parent root = FXMLLoader.load(getClass().getResource("/Dashboard/DashBoard.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showError(String message) {
        // Example: Show error alert dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        // Example: Show success alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
