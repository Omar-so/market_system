package cart;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CartController {

    @FXML
    private ListView<String> cartListView;
    @FXML
    private Label totalPriceLabel;

    private List<String> cartItems = new ArrayList<>();
    private String name;

  @FXML
public void initialize() {
    // This will be executed before `setName` is called
    System.out.println("CartController initialized, name: " + name); // Debugging
}

public void postInitialize() throws IOException {
   loadCartItems();
}


    public void setName(String t) {
        name = t;
    }

 public void loadCartItems() throws IOException {
     System.out.println("Name: " + name);

    String filePath = "/home/omar-so/NetBeansProjects/Main/src/userdashboard/"+name+".txt";
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println("File does not exist: " + filePath);
        showAlert("Error", "Cart file not found!");
        return;
    }

    cartItems = Files.readAllLines(file.toPath());
    cartListView.getItems().addAll(cartItems);
    updateTotalPrice();
}



    // Method to delete the selected product from the cart
    @FXML
    private void deleteProduct() {
        String selectedProduct = cartListView.getSelectionModel().getSelectedItem();
         System.out.println("cart.CartController.deleteProduct()");
        if (selectedProduct != null) {
            // Remove from cart list and ListView
            cartItems.remove(selectedProduct);
            cartListView.getItems().remove(selectedProduct);
            updateTotalPrice();

            // Update the file to reflect the change
            updateCartFile();
        } else {
            showAlert("No Product Selected", "Please select a product to delete.");
        }
    }

    // Method to handle checkout
    @FXML
    private void checkout() throws IOException {
        loadCartItems();
        if (cartItems.isEmpty()) {
            showAlert("Empty Cart", "Your cart is empty. Please add some products.");
        } else {
            double totalAmount = 0.0;
            for (String item : cartItems) {
                String[] parts = item.split(" - ");
                double price = Double.parseDouble(parts[1].replace("$", ""));
                totalAmount += price;
            }

            showAlert("Checkout", "Your total is: $" + totalAmount);
        }
    }

    // Method to update the total price label
public void updateTotalPrice() {
    double totalPrice = 0.0;

    for (String line : cartItems) {
        try {
            // Assume the line format is "itemName itemID price"
            String[] parts = line.split(" ");
            if (parts.length < 3) {
                System.out.println("Invalid line format: " + line);
                continue; // Skip this line
            }
            
            // Parse the price (assuming it's the third element)
            double price = Double.parseDouble(parts[2]);
            totalPrice += price;
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format in line: " + line);
        }
    }

    System.out.println("Total Price: $" + totalPrice);
    // Update the UI (if necessary)
}


    // Helper method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to update the cart file after deleting a product
    private void updateCartFile() {
        String filePath = "/home/omar-so/NetBeansProjects/Main/src/userdashboard/" + name + ".txt";
        
        // Rewrite the file with the updated cart items
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String item : cartItems) {
                writer.write(item + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the cart file.");
        }
    }
}
