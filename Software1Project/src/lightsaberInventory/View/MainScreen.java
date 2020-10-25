package lightsaberInventory.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lightsaberInventory.Model.Inventory;
import lightsaberInventory.Model.Part;
import lightsaberInventory.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** MainScreen of LightSaber Inventory application. */
public class MainScreen implements Initializable {

    @FXML
    TextField partSearchBox;

    @FXML
    TextField productSearchBox;

    @FXML
    TableView<Part> partsTable = new TableView<Part>(Inventory.getAllParts());

    @FXML
    TableColumn<Part, Integer> partIdCol;

    @FXML
    TableColumn<Part, String> partNameCol;

    @FXML
    TableColumn<Part, Integer> partInventoryCol;

    @FXML
    TableColumn<Part,Integer> partPriceCol;

    @FXML
    TableView<Product> productTable = new TableView<Product>(Inventory.getAllProducts());

    @FXML
    TableColumn<Product, Integer> productIdCol;

    @FXML
    TableColumn<Product, String> productNameCol;

    @FXML
    TableColumn<Product, Integer> productInventoryCol;

    @FXML
    TableColumn<Product, Integer> productPriceCol;


    /** Search function applied to the Parts search bar. Filters the partsTable with parts matching inputted ID or name */
    @FXML
    public void searchParts() {
        if (partSearchBox.getText().trim().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }

        else {
            try {
                Part returnedPart = Inventory.lookupPart(Integer.parseInt(partSearchBox.getText()));

                if (returnedPart == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Parts Found");
                    alert.setHeaderText("Please Search Again");
                    alert.showAndWait();
                }
                else {
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(returnedPart);
                    partsTable.setItems(filteredPartsList);
                }

            }

            catch (NumberFormatException e) {
                System.out.println("Number Format Exception");
                partsTable.setItems(Inventory.lookupPart(partSearchBox.getText().trim()));
            }


        }

    }

    /** Search function applied to the Products search bar. Filters the productTable with parts matching inputted ID or name */
    @FXML
    public void searchProducts() {
        if (productSearchBox.getText().trim().isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());
        }

        else {
            try {
                Product returnedProduct = Inventory.lookupProduct(Integer.parseInt(productSearchBox.getText()));
                if (returnedProduct == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Products Found");
                    alert.setHeaderText("Please Search Again");
                    alert.showAndWait();
                }
                else {
                    ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
                    filteredProductList.add(returnedProduct);
                    productTable.setItems(filteredProductList);
                }

            }

            catch (NumberFormatException e) {
                System.out.println("Number Format Exception");
                productTable.setItems(Inventory.lookupProduct(productSearchBox.getText().trim()));
            }
        }

    }


    @FXML
    Button partAddButton;

    @FXML
    Button partModifyButton;

    @FXML
    Button partDeleteButton;

    /** Displays the addPart view. Allows users to add a part the part table.
     @param screenAddParts An ActionEvent to help scene transition
     @throws IOException failed to read the file*/
    @FXML
    public void addPartsAction (ActionEvent screenAddParts) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
            Parent addPartScreen = loader.load();
            Scene addPartScene = new Scene(addPartScreen);
            Stage winAddPart = (Stage)((Node)screenAddParts.getSource()).getScene().getWindow();
            winAddPart.setTitle("Add Part");
            winAddPart.setScene(addPartScene);
            winAddPart.show();
        }
        catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /** Displays the modifyPart view. Allows users to modify a selected part from part table.
     @param event An ActionEvent to help scene transition
     @throws IOException failed to read the file*/
    @FXML
    public void modifyPartAction (ActionEvent event) throws IOException {

        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a part to modify");
            alert.showAndWait();

        }
        else {

            Part selectedItem = partsTable.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem.getName());

            ModifyPartController.displayedPart = selectedItem;


            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            Parent addPartScreen = loader.load();
            Scene addPartScene = new Scene(addPartScreen);
            Stage winAddPart = (Stage)((Node)event.getSource()).getScene().getWindow();
            winAddPart.setTitle("Modify Part");
            winAddPart.setScene(addPartScene);
            winAddPart.show();
            }
            catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }



    }

    /** Deletes a part from the partTable. Allows users to delete a selected part from part table. */
    @FXML
    public void deletePartsAction() {
        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a part to modify");
            alert.showAndWait();

        }

        else {
            Part selectedItem = partsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part?");
            alert.setHeaderText("Delete " + selectedItem.getName() + "?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK)  {
                Inventory.deletePart(selectedItem);
                partsTable.getItems().remove(selectedItem);


            }
            else {
                alert.close();
            }

        }

    }


    // Product Section

    /** Displays the addProduct view. Allows users to add a product the product table.
     @param screenAddProducts An ActionEvent to help scene transition
     @throws IOException failed to read the file */
    @FXML
    public void addProductAction (ActionEvent screenAddProducts) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
            Parent addPartScreen = loader.load();
            Scene addPartScene = new Scene(addPartScreen);
            Stage winAddPart = (Stage)((Node)screenAddProducts.getSource()).getScene().getWindow();
            winAddPart.setTitle("Add Part");
            winAddPart.setScene(addPartScene);
            winAddPart.show();
        }
        catch (IOException e) { assert true; }
    }

    /** Displays the modifyProduct view. Allows users to modify a selected product from product table.
     @param event An ActionEvent to help scene transition */
    @FXML
    public void modifyProductAction(ActionEvent event) {
        if (productTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a product to modify");
            alert.showAndWait();

        }
        else {

            Product selectedItem = productTable.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem.getName());

            ModifyProductController.displayedProduct = selectedItem;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                Parent addPartScreen = loader.load();
                Scene addPartScene = new Scene(addPartScreen);
                Stage winAddPart = (Stage)((Node)event.getSource()).getScene().getWindow();
                winAddPart.setTitle("Modify Product");
                winAddPart.setScene(addPartScene);
                winAddPart.show();
            }
            catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }

    /** Deletes a product from the productTable. If no associated parts exist, deletes a selected product from product table. */
    @FXML
    public void deleteProductAction() {
        if (productTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a part to modify");
            alert.showAndWait();

        }
        else {
            Product selectedItem = productTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product?");
            alert.setHeaderText("Delete " + selectedItem.getName() + "?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK)  {

                if (!selectedItem.getAllAssociatedParts().isEmpty()) {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setTitle("Delete Error");
                    newAlert.setHeaderText("Products cant be delete if they have associated parts");
                    newAlert.showAndWait();
                }
                else {
                    Inventory.deleteProduct(selectedItem);
                    productTable.getItems().remove(selectedItem);
                }

            }
            else {
                alert.close();
            }




        }
    }


    /** Populates the partsTable with parts. Gets parts list from Inventory. */
    @FXML
    public void updateLightsaberParts() {
        partsTable.setItems(Inventory.getAllParts());
    }

    /** Populates the productsTable with products. Gets product list from Inventory. */
    @FXML
    public void updateLightsaberProducts() {
        productTable.setItems(Inventory.getAllProducts());
    };

    /** Terminates the app. If users confirms, Lightsaber Inventory is terminated.
     * @param event An ActionEvent to help scene transition */
    @FXML
    public void exitButtonAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Lightsaber Inventory");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage winMainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
            winMainScreen.close();
        }
        else {
            alert.close();
        }
    }




    /** Loads the partsTable and productTable with columns names.  Helper function do to any necessary data population after scene is loaded. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateLightsaberParts();

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateLightsaberProducts();

    }
}
