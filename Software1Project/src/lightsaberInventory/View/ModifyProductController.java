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
import lightsaberInventory.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** ModifyProduct controller of LightSaber Inventory application. */
public class ModifyProductController implements Initializable {

    static public Product displayedProduct;

    @FXML
    TextField textID;

    @FXML
    TextField textName;

    @FXML
    TextField textInventory;

    @FXML
    TextField textPrice;

    @FXML
    TextField textMin;

    @FXML
    TextField textMax;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    @FXML
    TextField searchPartsBox;

    @FXML
    TableView<Part> allPartsTable;

    @FXML
    TableColumn<Part, Integer> allPartsPartIDCol;

    @FXML
    TableColumn<Part, Integer> allPartsNameCol;

    @FXML
    TableColumn<Part,String>allPartsInvCol;

    @FXML
    TableColumn<Part, Integer> allPartsPriceCol;

    @FXML
    TableView<Part> associatedPartsTable;

    @FXML
    TableColumn<Part, Integer> asscPartsPartIDCol;

    @FXML
    TableColumn<Part, String> asscPartsNameCol;

    @FXML
    TableColumn<Part,String>assocPartsInvCol;

    @FXML
    TableColumn<Part, Integer> assocPartsPriceCol;

    @FXML
    Button addPartButton;

    @FXML
    Button removeAssicatedPartButton;


    /** Displays MainScreen and does not save product changes. The modifications to the product instance are abandoned.
     * @throws IOException failed to read the file */
    @FXML
    void setCancelButton(ActionEvent event) throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel?");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                Parent addPartScreen = loader.load();
                Scene addPartScene = new Scene(addPartScreen);
                Stage winAddPart = (Stage)((Node)event.getSource()).getScene().getWindow();
                winAddPart.setTitle("Add Part");
                winAddPart.setScene(addPartScene);
                winAddPart.show();
            }
            else {
                alert.close();
            }
        }
        catch (IOException E) {
            System.out.println(E.getLocalizedMessage());
        }

    };

    /** Saves modifications to product and displays MainScreen. The old product is replaced by new product at the index of old product.
     * Checks include, but are not limited to: Min is less than Inventory is less than Max, all textFields are filled in.
     * @param event An ActionEvent to help scene transition
     * @throws IOException failed to read the file
     * @throws NumberFormatException inputted a string in a field designed for integers */
    @FXML
    public void saveProductButtonPressed(ActionEvent event) throws IOException, NumberFormatException {
        if (textName.getText().isEmpty()
                || textInventory.getText().isEmpty()
                || textMin.getText().isEmpty()
                || textMax.getText().isEmpty()
                || textPrice.getText().isEmpty()) {

            System.out.println("Data Empty");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Error");
            alert.setHeaderText("Please enter valid data for every field");
            alert.showAndWait();
        }

        else if (!Inventory.isNumeric(textMax.getText())
                || !Inventory.isNumeric(textMin.getText())
                || !Inventory.isNumeric(textPrice.getText())
                || !Inventory.isNumeric(textInventory.getText())) {


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Value Error");
            alert.setHeaderText("Min, Max, Inventory, and price should all be numeric");
            alert.showAndWait();

        }

        else if (Integer.parseInt(textMin.getText()) > Integer.parseInt(textMax.getText())) {
            System.out.println("Min Max Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Min Max Error");
            alert.setHeaderText("Product Mins cannot be greater than Maxs");
            alert.showAndWait();
        }

        else if (Integer.parseInt(textMin.getText()) > Integer.parseInt(textInventory.getText()) || Integer.parseInt(textInventory.getText()) > Integer.parseInt(textMax.getText())) {
            System.out.println("Inventory Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inventory Error");
            alert.setHeaderText("Inventory should be between the min and max");
            alert.showAndWait();
        }

        else {
            System.out.println("Data not empty");
            try {
                int index = displayedProduct.getId();

                String name = textName.getText();
                int invText = Integer.parseInt(textInventory.getText());
                double price = (Double.parseDouble(textPrice.getText()));
                int min = Integer.parseInt(textMin.getText());
                int max = Integer.parseInt(textMax.getText());

                Product newProduct = new Product(index, name, price, invText, min, max, associatedPartsTable.getItems());
                System.out.println(newProduct);
                Inventory.updateProduct(index - 1001, newProduct);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                Parent addPartScreen = loader.load();
                Scene addPartScene = new Scene(addPartScreen);
                Stage winAddPart = (Stage)((Node)event.getSource()).getScene().getWindow();
                winAddPart.setTitle("Main Screen");
                winAddPart.setScene(addPartScene);
                winAddPart.show();
            }
            catch (IOException E) {
                System.out.println(E.getLocalizedMessage());
            }
            catch ( NumberFormatException E) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Error");
                alert.setHeaderText("Please format your inputs like the following:" +
                        "\nName: String" +
                        "\nPrice: Double" +
                        "\nMin, Max, Inventory: Integer");
                alert.showAndWait();
            }



        }

    }


    /** Associates a selected part with the product. If no part is selected display an alert error */
    @FXML
    public void addPartButtonPressed() {
        if (allPartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a part to add");
            alert.showAndWait();
        }

        else {
            Part selectedItem = allPartsTable.getSelectionModel().getSelectedItem();
            associatedPartsTable.getItems().add(selectedItem);
        }
    }


    /** Disassociates a selected part with the product. Confirmation alert is shown. */
    @FXML
    public void setRemoveAssociatedPartButton() {
        Part selectedItem = associatedPartsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Part Association?");
        alert.setHeaderText("Remove Association for part: " + selectedItem.getName() + "?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            associatedPartsTable.getItems().remove(selectedItem);
        }
        else {
            alert.close();
        }
    }


    /** Loads the all parts for the allPartsTable.  Helper function do to any necessary data population after scene is loaded. */
    @FXML
    public void updateAllPartsTable() {
        allPartsTable.setItems(Inventory.getAllParts());
    }



    /** Loads the associated parts for the product.  Helper function do to any necessary data population after scene is loaded. */
    @FXML
    public void  updateAssociatedPartsTable() {
        associatedPartsTable.setItems(displayedProduct.getAllAssociatedParts());
    }


    /** Search function applied to the parts search bar. Filters the allPartsTable with parts matching inputted ID or name */
    @FXML
    public void searchParts() {
        if (searchPartsBox.getText().trim().isEmpty()) {
            allPartsTable.setItems(Inventory.getAllParts());
        }

        else {
            try {
                Part returnedPart = Inventory.lookupPart(Integer.parseInt(searchPartsBox.getText()));

                if (returnedPart == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Parts Found");
                    alert.setHeaderText("Please Search Again");
                    alert.showAndWait();
                }
                else {
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(returnedPart);
                    allPartsTable.setItems(filteredPartsList);
                }
            }

            catch (NumberFormatException e) {
                System.out.println("Number Format Exception");
                allPartsTable.setItems(Inventory.lookupPart(searchPartsBox.getText().trim()));
            }
        }

    }


    /** Loads the allPartsTable and assocPartsTable with columns names. Helper function do to any necessary data population after scene is loaded. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateAllPartsTable();

        textID.setText(String.valueOf(displayedProduct.getId()));
        textName.setText(displayedProduct.getName());
        textInventory.setText(String.valueOf(displayedProduct.getStock()));
        textPrice.setText(String.valueOf((displayedProduct.getPrice())));
        textMax.setText(String.valueOf(displayedProduct.getMax()));
        textMin.setText(String.valueOf(displayedProduct.getMin()));

        asscPartsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asscPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateAssociatedPartsTable();

    }
}
