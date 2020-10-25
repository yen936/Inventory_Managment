package lightsaberInventory.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lightsaberInventory.Model.InHouse;
import lightsaberInventory.Model.Inventory;
import lightsaberInventory.Model.Outsourced;
import lightsaberInventory.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** AddPart controller of LightSaber Inventory application. */
public class AddPartController implements Initializable {

    @FXML
    RadioButton InHouseRadio;

    @FXML
    RadioButton OutsourcedRadio;

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
    TextField textDualPurpose;

    @FXML
    Label dualPurpLabel;

    @FXML
    Button addButton;

    @FXML
    Button cancelButton;




    /** Updates the UI of based on Radio Buttons.
     Builds the ToggleGroup for RadioButtons, and changes the dualPurpLabel based on selection. */
    @FXML
    public void updateRadioUI() {
        ToggleGroup Tgroup = new ToggleGroup();
        InHouseRadio.setToggleGroup(Tgroup);
        OutsourcedRadio.setToggleGroup(Tgroup);

        if (InHouseRadio.isSelected()) {
            dualPurpLabel.setText("Machine ID");
        }
        else if (OutsourcedRadio.isSelected()) {
            dualPurpLabel.setText("Company Name");
        }

    };


    /** Displays MainScreen and does not save user changes. The new part instance is abandoned.
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


    /** Saves new part and displays MainScreen. The new part instance is added to allParts if it passes error checks.
     * Checks include, but are not limited to: Min is less than Inventory is less than Max, all textFields are filled in.
     * @throws IOException failed to read the file
     * @throws NumberFormatException inputted a string in a field designed for integers
     * @param event An ActionEvent to help scene transition*/
    @FXML
    public void addPartButtonPressed(ActionEvent event) throws IOException, NumberFormatException {
        if (textName.getText().isEmpty()
                || textInventory.getText().isEmpty()
                || textDualPurpose.getText().isEmpty()
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
            alert.setHeaderText("Part Mins cannot be greater than Maxs");
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

            System.out.println(!Inventory.isNumeric(textInventory.getText()));

            System.out.println("Data not empty");
            try {
                int id = Integer.parseInt(textID.getText());
                String name = textName.getText();
                int invText = Integer.parseInt(textInventory.getText());
                double price = Double.parseDouble(textPrice.getText());
                int min = Integer.parseInt(textMin.getText());
                int max = Integer.parseInt(textMax.getText());
                String dualText = textDualPurpose.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Part");
                alert.setHeaderText("Would like to save this part to the inventory? ");
                alert.showAndWait();


                if (alert.getResult() == ButtonType.OK)  {


                    if (InHouseRadio.isSelected()) {
                        InHouse inhousePart = new InHouse(id, name, price, invText, min, max, Integer.parseInt(dualText));
                        inhousePart.setMachineId(Integer.parseInt(dualText));
                        System.out.println(inhousePart.getMachineId());
                        Inventory.addPart(inhousePart);

                    }
                    else if (OutsourcedRadio.isSelected()) {
                        System.out.println("Saving Outsourced");
                        Outsourced outsourcedPart = new Outsourced(id, name, price, invText, min, max, dualText);
                        outsourcedPart.setCompanyName(dualText);
                        System.out.println(outsourcedPart.getCompanyName());
                        Inventory.addPart(outsourcedPart);
                    }


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage mainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                    mainScreen.setScene(scene);
                    mainScreen.show();

                }
                else {
                    alert.close();
                }
            }
            catch (IOException E) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Error");
                alert.setHeaderText(E.getLocalizedMessage());
                alert.showAndWait();
            }
            catch (NumberFormatException E) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Error");
                alert.setHeaderText("Please format your inputs like the following:" +
                        "\nName: String" +
                        "\nPrice: Double" +
                        "\nMin, Max, Inventory: Integer" +
                        "\nMachine ID: Number " +
                        "\nCompany Name: String");
                alert.showAndWait();
            }


        }



    }


    /** Sets InHouseRadio to selected. Initializes a part ID Helper function do to any necessary data population after scene is loaded. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InHouseRadio.setSelected(true);

        Part lastPart = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1);
        int lastID = lastPart.getId();
        textID.setText(String.valueOf(++lastID));

    }
}
