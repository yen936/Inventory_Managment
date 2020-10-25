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


/** ModifyPart controller of LightSaber Inventory application. */
public class ModifyPartController  implements Initializable {

    public static Part displayedPart;


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
    Button saveButton;

    @FXML
    Button cancelButton;


    /** Updates the UI of based on Radio Buttons.
     Builds the ToggleGroup for RadioButtons, and changes the dualPurpLabel based on selection. */
    public void setupView() {
        ToggleGroup Tgroup = new ToggleGroup();
        InHouseRadio.setToggleGroup(Tgroup);
        OutsourcedRadio.setToggleGroup(Tgroup);

        textID.setText(String.valueOf(displayedPart.getId()));
        textName.setText(displayedPart.getName());
        textInventory.setText(String.valueOf(displayedPart.getStock()));
        textPrice.setText(String.valueOf(displayedPart.getPrice()));
        textMax.setText(String.valueOf(displayedPart.getMax()));
        textMin.setText(String.valueOf(displayedPart.getMin()));

        if (displayedPart instanceof InHouse) {
            InHouseRadio.setSelected(true);
            dualPurpLabel.setText("Machine ID");
            textDualPurpose.setText(Integer.toString(((InHouse) displayedPart).getMachineId()));
        }
        else {
            OutsourcedRadio.setSelected(true);
            dualPurpLabel.setText("Company Name");
            textDualPurpose.setText(((Outsourced) displayedPart).getCompanyName());
        }

    }


    /** Saves modifications to part and displays MainScreen. The old part is replaced by new part at the index of old part .
     * Checks include, but are not limited to: Min is less than Inventory is less than Max, all textFields are filled in.
     * @param event An ActionEvent to help scene transition
     * @throws IOException failed to read the file
     * @throws NumberFormatException inputted a string in a field designed for integers */
    @FXML
    public void saveMod(ActionEvent event) throws IOException, NumberFormatException {

        if (!Inventory.isNumeric(textMax.getText())
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
            try {
                int index = displayedPart.getId();

                String name = textName.getText();
                int invText = Integer.parseInt(textInventory.getText());
                double price = (Double.parseDouble(textPrice.getText()));
                int min = Integer.parseInt(textMin.getText());
                int max = Integer.parseInt(textMax.getText());
                String dualText = textDualPurpose.getText();

                if (InHouseRadio.isSelected()) {

                    if (!Inventory.isNumeric(dualText)) {
                        MachineIDError();
                    }
                    else {
                        InHouse inhousePart = new InHouse(index, name, price, invText, min, max, Integer.parseInt(dualText));
                        Inventory.updatePart(index, inhousePart);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                        Parent addPartScreen = loader.load();
                        Scene addPartScene = new Scene(addPartScreen);
                        Stage winAddPart = (Stage)((Node)event.getSource()).getScene().getWindow();
                        winAddPart.setTitle("Add Part");
                        winAddPart.setScene(addPartScene);
                        winAddPart.show();
                    }
                }

                else if (OutsourcedRadio.isSelected()) {

                    if (Inventory.isNumeric(dualText)) {
                        CompanyNameError();
                    }
                    else {
                        Outsourced outsourcedPart = new Outsourced(index, name, price, invText, min, max, dualText);
                        Inventory.updatePart(index, outsourcedPart);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                        Parent addPartScreen = loader.load();
                        Scene addPartScene = new Scene(addPartScreen);
                        Stage winAddPart = (Stage)((Node)event.getSource()).getScene().getWindow();
                        winAddPart.setTitle("Add Part");
                        winAddPart.setScene(addPartScene);
                        winAddPart.show();
                    }

                }


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
                        "\nMin, Max, Inventory: Integer" +
                        "\nMachine ID: Number " +
                        "\nCompany Name: String");
                alert.showAndWait();
            }

        }


    }

    /** Displays an alert error based on the machineID TextField. */
    public void MachineIDError() {
        System.out.println("Machine Id Error");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Machine Id Error");
        alert.setHeaderText("Machine ID should be an Integer");
        alert.showAndWait();
    }

    /** Displays an alert error based on the companyName TextField. */
    public void CompanyNameError() {
        System.out.println("Company Name Error");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Company Name Error");
        alert.setHeaderText("Company Name should be an String");
        alert.showAndWait();
    }


    /** Updates the UI of based on Radio Buttons. Changes the dualPurpLabel based on selection. */
    @FXML
    public void updateRadioUI() {
        if (InHouseRadio.isSelected()) {
            dualPurpLabel.setText("Machine ID");
        }
        else if (OutsourcedRadio.isSelected()) {
            dualPurpLabel.setText("Company Name");
        }

    };


    /** Displays MainScreen and does not save part changes. The modifications to the part instance are abandoned.
     * @throws IOException failed to read the file */
    @FXML
    void setCancelButton(ActionEvent event) throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel?");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Press OK to discard edits.");
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

    }


    /** Helper function do to any necessary data population after scene is loaded. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupView();
    }

}
