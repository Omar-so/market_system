package Dashboard;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashBoardController implements Initializable {

    @FXML
    private Label plabel, emp;
    @FXML
    private Label setTitle;

    @FXML
    public void productbutton(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/FXML.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void EMpbutton(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/emp/Employee.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Setting(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Account/account.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void settitle(String s) {
        setTitle.setText("Welcome back, " + s + "😎");
    }

    @FXML
    public void Logout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/signup/signup.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Path filePath = Path.of("/home/omar-so/NetBeansProjects/Main/src/main/Product.txt");
        Path filePath1 = Path.of("/home/omar-so/NetBeansProjects/Main/src/emp/Employee.txt");

        try {
            long lineCount = Files.lines(filePath).count();
            plabel.setText(String.valueOf(lineCount));
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        try {
            long lineCount = Files.lines(filePath1).count();
            emp.setText(String.valueOf(lineCount));
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}
