package userAccount;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
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
import userdashboard.UserdashboardController;

/**
 * FXML Controller class
 *
 * @author omar-so
 */
public class AccountController_1 implements Initializable {
    
    @FXML
    private TextField currentUsernameField, newUsernameField;
    @FXML
    private TextField currentPasswordField, newPasswordField;
    
    private final String path = "/home/omar-so/NetBeansProjects/Main/src/signup/user.txt";
    private final String tempPath = "/home/omar-so/NetBeansProjects/Main/src/signup/user_temp.txt";
    
    String name;    

    public void setname(String Name) {
        name = Name;
    }

    @FXML
    public void Logout(ActionEvent e) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userdashboard/userdashboard.fxml"));
        Parent root = loader.load();
        UserdashboardController ds = loader.getController();
        ds.settitle(name);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void changebutt(ActionEvent ee) {
        String currUsername = currentUsernameField.getText();
        String currPassword = currentPasswordField.getText();
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();
        
        boolean found = false;

        // Check if the file exists
        if (!Files.exists(Paths.get(path))) {
            showError("User file not found.");
            return;
        }

        // Perform file reading and writing
        try (BufferedReader br = new BufferedReader(new FileReader(path)); FileWriter fw = new FileWriter(tempPath)) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data.length == 2 && data[0].equals(currUsername) && data[1].equals(currPassword)) {
                    fw.write(newUsername + " " + newPassword + "\n");
                    found = true;
                } else {
                    fw.write(line + "\n");
                }
            }
            
        } catch (IOException e) {
            showError("Error reading or writing files: " + e.getMessage());
            return;
        }

        // Replace the old file with the new one if successful
        if (found) {
            try {
                Files.delete(Paths.get(path));
                Files.move(Paths.get(tempPath), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
                showSuccess("You Changed it Successfully");
            } catch (IOException e) {
                showError("Error updating the file: " + e.getMessage());
            }
        } else {
            // Delete the temporary file if the user is not found
            try {
                Files.deleteIfExists(Paths.get(tempPath));
            } catch (IOException ignored) {
            }
            showError("Wrong Username or Password.");
        }
    }
    
    @FXML
    public void reserbutt(ActionEvent e) {
        // Clear all input fields
        currentPasswordField.clear();
        currentUsernameField.clear();
        newPasswordField.clear();
        newUsernameField.clear();
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }
}
