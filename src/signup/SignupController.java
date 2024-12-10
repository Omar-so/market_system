package signup;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import Dashboard.DashBoardController;
import java.io.BufferedWriter;

public class SignupController implements Initializable {

    @FXML
    private TextField usernameField, emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox admin;

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

    @FXML
    public void signupbutton(ActionEvent event) throws IOException {
        String adminPath = "/home/omar-so/NetBeansProjects/Main/src/signup/admin.txt";
        String userPath = "/home/omar-so/NetBeansProjects/Main/src/signup/user.txt";

        String path = admin.isSelected() ? adminPath : userPath;
        boolean usernameExists = false;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0].equals(usernameField.getText())) {
                    showError("Username already exists. Please choose another username.");
                    usernameExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            showError("An error occurred while reading the file: " + e.getMessage());
            return;
        }

        if (!usernameExists) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
                bw.write(usernameField.getText() + " " + passwordField.getText() + "\n");
                showSuccess("Signup successful!");
            } catch (IOException e) {
                showError("An error occurred while writing to the file: " + e.getMessage());
            }
        }
    }

    @FXML
    public void signinbutton(ActionEvent event) throws IOException {
        String path1 = "/home/omar-so/NetBeansProjects/Main/src/signup/admin.txt";
        String path2 = "/home/omar-so/NetBeansProjects/Main/src/signup/user.txt";
        boolean found = false;
        String selectedPath = admin.isSelected() ? path1 : path2;

        try (BufferedReader br = new BufferedReader(new FileReader(selectedPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] myarray = line.split(" ");
                if (myarray[0].equals(usernameField.getText()) && myarray[1].equals(passwordField.getText())) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard/DashBoard.fxml"));
                    Parent root = loader.load();
                    DashBoardController ds = loader.getController();
                    ds.settitle(usernameField.getText());

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            showError("Error reading the file: " + e.getMessage());
        }

        if (!found) {
            showError("Wrong username or password");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Optional initialization code
    }
}
