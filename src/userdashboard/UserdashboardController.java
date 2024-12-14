package userdashboard;

import Dashboard.DashBoardController;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import cart.CartController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.TextField;
import userAccount.AccountController_1;
public class UserdashboardController implements Initializable {

    @FXML
    private GridPane Panel;
    @FXML
    private Label setTitle;

    private String name;

    public void settitle(String s) {
        setTitle.setText("Welcome back, " + s + " 😎");
        name = s;
    }

    @FXML
    void Cartbutton(ActionEvent event) throws IOException {
        String path = "/cart/Cart.fxml";  // Path to FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();

        // Get controller and set the name
        CartController controller = loader.getController();
        controller.setName(name);

        // Call postInitialize explicitly after setting the name
        controller.postInitialize();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configure the GridPane
        Panel.setHgap(20);
        Panel.setVgap(20);
        Panel.setPadding(new Insets(20));
        Panel.setAlignment(Pos.CENTER);

        // Path to the product file
        Path filePath = Path.of("/home/omar-so/NetBeansProjects/Main/src/main/Product.txt");

        try {
            // Read all lines from the product file
            List<String> lines = Files.readAllLines(filePath);

            // Calculate number of columns (adjust as needed)
            int columns = 3;
            int row = 0;
            int col = 0;

            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String productName = parts[1].trim();
                    String productPrice = parts[2].trim();
                    String productImage = parts[2].trim(); // Assuming third part is image path
                    System.out.println("Read line: " + line);

                    // Add product to the GridPane
                    addProductToPanel(productName, productPrice, productImage, row, col);

                    // Move to next column/row
                    col++;
                    if (col >= columns) {
                        col = 0;
                        row++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the product file: " + e.getMessage());
        }
    }

    // Helper method to add a product to the GridPane
    private void addProductToPanel(String productName, String productPrice, String imagePath, int row, int col) {
        // Create a VBox to hold product details
        VBox productBox = new VBox(10);
        productBox.setAlignment(Pos.CENTER);
        productBox.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 0);"
                + "-fx-padding: 15;"
        );

//        // Add product image
//        ImageView imageView = new ImageView();
//        try {
//            // Use FileInputStream to load the image
//            Image image = new Image(new FileInputStream(imagePath));
//            imageView.setImage(image);
//            imageView.setFitWidth(150);
//            imageView.setFitHeight(150);
//            imageView.setPreserveRatio(true);
//        } catch (Exception e) {
//            // Fallback if image can't be loaded
//            imageView.setFitWidth(150);
//            imageView.setFitHeight(150);
//            imageView.setStyle("-fx-background-color: lightgray;");
//            System.err.println("Error loading image: " + e.getMessage());
//        }
        // Product name label
        Label nameLabel = new Label(productName);
        nameLabel.setStyle(
                "-fx-font-weight: bold;"
                + "-fx-font-size: 16px;"
                + "-fx-text-fill: red;"
        );

        // Product price label
        Label priceLabel = new Label("$" + productPrice);
        priceLabel.setStyle(
                "-fx-text-fill: #2ecc71;"
                + // Green color for price
                "-fx-font-size: 14px;"
        );
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        quantityField.setPrefWidth(60); // Set a reasonable width
        quantityField.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-border-color: #ccc;"
                + "-fx-border-radius: 5px;"
                + "-fx-padding: 5px;"
        );

        // Add to Cart button
        Button addToCartButton = new Button("Add To Cart");
        addToCartButton.setStyle(
                "-fx-background-color: #3498db;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 5px;"
        );

        // Button action to add product to cart
        addToCartButton.setOnAction(event -> {
            String path = "/home/omar-so/NetBeansProjects/Main/src/userdashboard/" + name + ".txt";
            try {
                java.io.File file = new java.io.File(path);
                if (!file.exists() && file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                }
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("Product Name : " + productName + " ,  Product Price : " + productPrice + " , Product Quntity :" + quantityField.getText() + "\n");
                    System.out.println("Added to cart: " + productName + " - $" + productPrice);

                    // Optional: Show a temporary success message
                    addToCartButton.setText("Added ✓");
                    addToCartButton.setStyle(
                            "-fx-background-color: #2ecc71;"
                            + "-fx-text-fill: white;"
                            + "-fx-background-radius: 5px;"
                    );

                    // Reset button after 2 seconds
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            Platform.runLater(() -> {
                                addToCartButton.setText("Add to Cart");
                                addToCartButton.setStyle(
                                        "-fx-background-color: #3498db;"
                                        + "-fx-text-fill: white;"
                                        + "-fx-background-radius: 5px;"
                                );
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException ex) {
                Logger.getLogger(UserdashboardController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error while creating or writing to file: " + ex.getMessage());
            }
        });

        // Add all components to the product box
        productBox.getChildren().addAll(
                //            imageView,
                nameLabel,
                priceLabel,
                quantityField,
                addToCartButton
        );

        // Add the product box to the GridPane
        Panel.add(productBox, col, row);
    }

    @FXML
    public void Setting(ActionEvent e) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/userAccount/acc.fxml"));
            Parent root = loader.load();
                        AccountController_1 ds = loader.getController();
                        ds.setname(name);
                    Stage stage = (Stage) ((Node )e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
    }

    @FXML
    public void Logout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/signup/signup.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
