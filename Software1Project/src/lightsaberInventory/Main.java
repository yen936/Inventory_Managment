package lightsaberInventory;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lightsaberInventory.View.MainScreen;

/** Main JavaFX application class. <BR>
 1. A compatible feature update this application would add pictures to the part and products.
 This would begin to resemble production IMS's in helping users more easily identify the object they are looking for.
 Users could upload pictures from their local machine, primarily through the FileDialog(Dialog parent) API.
 I would add a column to the left of every table for the user uploaded pictures for each part/product. If no picture was uploaded
 the app would show a default picture of a wrench icon for a part and a lightsaber icon for a product. <BR>
 2. One logical error I encountered (and the reason the project needed revisions) was the apparent not saving of changes to the addPart and modifyPart screens.
 The application appeared to save every change as an InHouse instance and the companyName / MachineID were not displayed in the modifyPart screen.
 Solution: Both errors were actually one. The apparent "saving" of every instance as InHouse was false--instances were saving correctly.
 I did not update the toggles according to the part displayed; rather the application defaulted to toggling InHouse.
 To correctly identify the toggle the application should show, I called the isInstanceOf() method.
 Which returns true if the parameter object is an instance of a supplied class.
 Then companyName / MachineID not being displayed were simple to solve. First the  getCompanyName() / getMachineID() were unable to be called
 because displyedPart was of type Part. But by passing in the two Part sub classes--Outsourced and InHouse--the application was get company name
 and the machineID to update the view. */
public class Main extends Application {

    /** Start Lightsaber Inventory JavaFX application. */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("MainScreen.fxml"));

            Parent root = fxmlLoader.load();
            MainScreen mainScreen = fxmlLoader.getController();
            Scene scene = new Scene(root);

            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Main JavaFX function call.
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}


