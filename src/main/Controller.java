package main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Models.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML
    private TextField Name, Number, Price,SearchBar;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TableColumn<Product, String> ProductNumber;

    @FXML
    private TableColumn<Product, String> ProductPrice;

    @FXML
    private TableView<Product> table;

    private ObservableList<Product> productList;
    
    public static int NumberOfProduct = 0;
    @FXML
        private void AddButton(ActionEvent event) throws IOException {
            String GetName = Name.getText();
            String GetNumber = Number.getText();
            String GetPrice = Price.getText();

            // Check if any of the fields are empty
            if (GetName.isEmpty() || GetNumber.isEmpty() || GetPrice.isEmpty()) {
                // Show a pop-up message
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("All fields are required!");
                alert.setContentText("Please make sure all fields are filled in.");
                alert.showAndWait();
            } else {
                // Proceed with adding the product if all fields are filled
                try (FileWriter myWriter = new FileWriter("/home/omar-so/NetBeansProjects/Main/src/main/Product.txt", true)) {
                    myWriter.write(GetNumber + " " + GetName + " " + GetPrice + "\n");
                } catch (Exception e) {
                    System.out.println("File doesn't work");
                }

                // Create a new Product instance
                Product item = new Product(GetNumber, GetName, GetPrice);
                productList.add(item);  // Add to ObservableList

                table.refresh(); // Refresh TableView to show the updated list

                // Clear the input fields after adding the product
                Name.clear();
                Number.clear();
                Price.clear();
            }
        }


                @FXML
        private void UpdateButton(ActionEvent event) {
            Product selectedProduct = table.getSelectionModel().getSelectedItem();
            String path = "/home/omar-so/NetBeansProjects/Main/src/main/Product.txt";
            String tempPath = "/home/omar-so/NetBeansProjects/Main/src/main/Product_temp.txt";

            if (selectedProduct != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(path));
                     FileWriter fw = new FileWriter(tempPath)) {

                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] myarray = line.split(" ");
                        if (myarray[0].equals(selectedProduct.getNumber())) { // Check if the line matches
                            // Replace line content with updated values
                            fw.write(Number.getText() + " " + Name.getText() + " " + Price.getText() + "\n");
                        } else {
                            // Write the original line back to the temporary file
                            fw.write(line + "\n");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Replace the original file with the updated temporary file
                try {
                    java.nio.file.Files.delete(java.nio.file.Paths.get(path)); // Delete original file
                    java.nio.file.Files.move(java.nio.file.Paths.get(tempPath), java.nio.file.Paths.get(path)); // Rename temp file
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Update the selected product in the TableView
                selectedProduct.setName(Name.getText());
                selectedProduct.setNumber(Number.getText());
                selectedProduct.setPrice(Price.getText());
                table.refresh(); // Refresh the TableView to reflect changes

                // Clear the input fields
                Name.clear();
                Number.clear();
                Price.clear();
            } else {
                System.out.println("No product selected for update");
            }
        }

       
      @FXML
      private void DeleteButton(ActionEvent event) {
          Product selectedProduct = table.getSelectionModel().getSelectedItem();
          String path = "/home/omar-so/NetBeansProjects/Main/src/main/Product.txt";
          String tempPath = "/home/omar-so/NetBeansProjects/Main/src/main/Product_temp.txt";
          if (selectedProduct != null) {
               try (BufferedReader br = new BufferedReader(new FileReader(path));
                     FileWriter fw = new FileWriter(tempPath)) {

                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] myarray = line.split(" ");
                        if (myarray[0].equals(selectedProduct.getNumber())) { // Check if the line matches
                           continue;
                        } else {
                            // Write the original line back to the temporary file
                            fw.write(line + "\n");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Replace the original file with the updated temporary file
                try {
                    java.nio.file.Files.delete(java.nio.file.Paths.get(path)); // Delete original file
                    java.nio.file.Files.move(java.nio.file.Paths.get(tempPath), java.nio.file.Paths.get(path)); // Rename temp file
                } catch (IOException e) {
                    e.printStackTrace();
                }
              productList.remove(selectedProduct); // Remove the selected item from the list
              Name.clear();
              Number.clear();
              Price.clear();
          } else {
              System.out.println("No product selected for deletion");
          }
      }
      
      
      
      
      
    @FXML
    private void SearchButton(ActionEvent event) {
        String searchName = SearchBar.getText().toLowerCase(); // Get the text to search for
        ObservableList<Product> filteredList = FXCollections.observableArrayList();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(searchName)) {
                filteredList.add(product);
            }
        }

        table.setItems(filteredList); // Display only matching items
    }
    @FXML
    private void RestButton(ActionEvent event){
        table.setItems(productList);
        SearchBar.clear();
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

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            productList = FXCollections.observableArrayList();
            try {
                FileReader fileReader = new FileReader("/home/omar-so/NetBeansProjects/Main/src/main/Product.txt");
                int i;
                String Num = "", Nam = "", Pri = "";
                int cnt = 1;
                while ((i = fileReader.read()) != -1) {
                    char currentChar = (char) i;
                    if (currentChar == ' ' && cnt == 1) {
                        cnt++;
                    } else if (currentChar == ' ' && cnt == 2) {
                        cnt++;
                    } else if (currentChar == '\n' || currentChar == '\r') {
                        Product p = new Product(Num, Nam, Pri);
                        productList.add(p);
                        NumberOfProduct++;
                        Num = "";
                        Nam = "";
                        Pri = "";
                        cnt = 1;
                    } else {
                        switch (cnt) {
                            case 1:
                                Num += currentChar; 
                                break;
                            case 2:
                                Nam += currentChar; 
                                break;
                            case 3:
                                Pri += currentChar; 
                                break;
                            default:
                                break;
                        }
                    }
                    
                }

                if (!Num.isEmpty() && !Nam.isEmpty() && !Pri.isEmpty()) {
                    Product p = new Product(Num, Nam, Pri);
                    productList.add(p);
                }

                fileReader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Correct column setup
          ProductNumber.setCellValueFactory(new PropertyValueFactory<>("number")); // Bind ProductNumber to Product's number field
          ProductName.setCellValueFactory(new PropertyValueFactory<>("name")); // Bind ProductName to Product's name field
          ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price")); // Bind ProductPrice to Product's price field

            table.setItems(productList);  // Set the items in the table
        }


}
