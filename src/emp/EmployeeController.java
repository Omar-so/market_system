package emp;

import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Models.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeController implements Initializable {

    @FXML
    private TextField Name, Number, Price, SearchBar;

    @FXML
    private TableColumn<Employee, String> ProductName;

    @FXML
    private TableColumn<Employee, String> ProductNumber;

    @FXML
    private TableColumn<Employee, String> ProductPrice;

    @FXML
    private TableView<Employee> table;

    private ObservableList<Employee> employeesList;
    public static int NumberOfProduct = 0;

    @FXML
    private void AddButton(ActionEvent event) throws IOException {
        String getName = Name.getText();
        String getNumber = Number.getText();
        String getPrice = Price.getText();

        if (getName.isEmpty() || getNumber.isEmpty() || getPrice.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("All fields are required!");
            alert.setContentText("Please make sure all fields are filled in.");
            alert.showAndWait();
            return;
        }

        try (FileWriter myWriter = new FileWriter("/home/omar-so/NetBeansProjects/Main/src/emp/Employee.txt", true)) {
            myWriter.write(getNumber + " " + getName + " " + getPrice + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Employee item = new Employee(getNumber, getName, getPrice);
        employeesList.add(item);
        table.refresh();

        Name.clear();
        Number.clear();
        Price.clear();
    }

    @FXML
    private void UpdateButton(ActionEvent event) {
        Employee selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No product selected for update!");
            alert.show();
            return;
        }

        String path = "/home/omar-so/NetBeansProjects/Main/src/emp/Employee.txt";
        String tempPath = "/home/omar-so/NetBeansProjects/Main/src/emp/Employee_temp.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path));
             FileWriter fw = new FileWriter(tempPath)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0].equals(selectedProduct.getId())) {
                    fw.write(Number.getText() + " " + Name.getText() + " " + Price.getText() + "\n");
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

        selectedProduct.setUsename(Name.getText());
        selectedProduct.setId(Number.getText());
        selectedProduct.setSalary(Price.getText());
        table.refresh();

        Name.clear();
        Number.clear();
        Price.clear();
    }

    @FXML
    private void DeleteButton(ActionEvent event) {
        Employee selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No product selected for deletion!");
            alert.show();
            return;
        }

        String path = "/home/omar-so/NetBeansProjects/Main/src/emp/Employee.txt";
        String tempPath = "/home/omar-so/NetBeansProjects/Main/src/emp/Employee_temp.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path));
             FileWriter fw = new FileWriter(tempPath)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (!data[0].equals(selectedProduct.getId())) {
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

        employeesList.remove(selectedProduct);
        Name.clear();
        Number.clear();
        Price.clear();
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
    @FXML
    private void SearchButton(ActionEvent event) {
        String searchName = SearchBar.getText().toLowerCase();
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();

        for (Employee product : employeesList) {
            if (product.getUsename().toLowerCase().contains(searchName)) {
                filteredList.add(product);
            }
        }

        table.setItems(filteredList);
    }

    @FXML
    private void RestButton(ActionEvent event) {
        table.setItems(employeesList);
        SearchBar.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeesList = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/omar-so/NetBeansProjects/Main/src/emp/Employee.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data.length == 3) {
                    employeesList.add(new Employee(data[0], data[1], data[2]));
                    NumberOfProduct++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProductNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("usename"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table.setItems(employeesList);
    }
}
