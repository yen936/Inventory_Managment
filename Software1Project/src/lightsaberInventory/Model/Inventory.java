package lightsaberInventory.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Inventory class that holds parts and product lists. */

public class Inventory {

    /** Constructor for allParts. Constructor defines all available parts and their attributes. */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList(
            new Outsourced(1, "Blue Kiber Crystal", 1200, 6, 0 , 5000, "Jedi Cave Dantuine"),
            new Outsourced(2, "Green Kiber Crystal", 1200, 5, 0, 5000, "Jedi Cave Dantuine"),
            new Outsourced(3, "Red Kiber Crystal", 1300, 4, 0, 500, "Sith Cave Koriban"),
            new InHouse(4, "Hilt", 174.99, 12, 0, 5000,2),
            new InHouse(5, "Magnetic Stabilizer Ring", 350, 8, 0, 5000, 1),
            new InHouse(6, "Activation Button", 78.56, 120, 0, 5000, 2),
            new InHouse(7, "Energy Focal Module", 550, 6, 0, 5000, 1)

    );

    /** Constructor for GuardianParts list. Constructor defines associated parts for the Jedi Guardian Saber. */
    private static ObservableList<Part> GuardianParts = FXCollections.observableArrayList(
            lookupPart(1),
            lookupPart(4),
            lookupPart(5),
            lookupPart(6),
            lookupPart(7)
    );

    /** Constructor for CounselorParts list. Constructor defines associated parts for the Jedi Counselor Saber. */
    private static ObservableList<Part> CounselorParts = FXCollections.observableArrayList(
            lookupPart(2),
            lookupPart(4),
            lookupPart(5),
            lookupPart(6),
            lookupPart(7)
    );
    /** Constructor for SithParts list. Constructor defines associated parts for the Sith Saber. */
    private static ObservableList<Part> SithParts = FXCollections.observableArrayList(
            lookupPart(3),
            lookupPart(4),
            lookupPart(5),
            lookupPart(6),
            lookupPart(7)
    );

    /** Constructor for allProducts list. Constructor defines all available products using the above associated parts lists. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(
            new Product(1001, "Jedi Guardian Saber", 7000, 5, 0, 5000, GuardianParts),
            new Product(1002, "Jedi Counselor Saber", 5000, 4,0,5000, CounselorParts),
            new Product(1003, "Sith Saber", 6000, 6, 0, 5000, SithParts)
    );


    /** Adds a Part to allParts. The addPart method adds a Part instance to the allParts list.
     @param newPart user defined Part instance
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    };


    /** Adds a product to allProducts. The addProduct method adds a Product instance to the allProducts list.
     @param newProduct user defined Product instance
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    };

    /** Returns part by PartID. The lookupPart method finds and returns a Part by its PartId.
     @param partID system-generated Part UUID
     @return the Part associated with that PartId or null
     */
    public static Part lookupPart(int partID) {
        for(Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    };

    /** Returns Product by ProductID. The lookupProduct method finds and returns a Product by its ProductId.
     @param productID system-generated Part UUID
     @return the Product associated with that ProductId or null
     */
    public static Product lookupProduct(int productID){
        for(Product prod  : allProducts) {
            if (prod.getId() == productID) {
                return prod;
            }
        }
        return null;
    };

    /**
     Returns list of Parts by part name. The lookupPart method finds and returns a Part by its ProductId.
     @param partName part name
     @return the Product associated with that part name or null
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
        for (Part p : allParts) {
            if (partName.compareTo(p.getName()) == 0) {
                filteredPartsList.add(p);
            }
        }
        return filteredPartsList;

    };

    /** Returns list of Products by product name. The lookupProduct method finds and returns a Product by its name.
     @param productName product name
     @return the Product associated with that product name or null
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
        for (Product p : allProducts) {
            if (productName.compareTo(p.getName()) == 0) {
                filteredProductList.add(p);
            }
        }
        return filteredProductList;

    };

    /** Updates parts at an index. The updatePart method modifies a Part at an index.
     @param index index position in the array
     @param selectedPart the updated part instance
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index - 1, selectedPart);
    };

    /** Updates a product at an index. The updateProduct method modifies Product at an index.
     @param index index position in the array
     @param newProduct the updated product instance
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }


    /** Removes a part from allParts. The deletePart method removes a part from the allParts attribute.
     @param selectedPart the part that will be deleted
     @return true if deleted else false
     */
    public static boolean deletePart(Part selectedPart) {
        int id = selectedPart.getId();
        Part lookupPart = lookupPart(id);

        return allParts.remove(lookupPart);
    };

    /** Removes a product from allProducts. The deletePart method removes a part from the allParts attribute.
     @param selectedProduct the product that will be deleted
     @return true if deleted else false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int id = selectedProduct.getId();
        Product lookupProduct = lookupProduct(id);
        return allProducts.remove(lookupProduct);
    }

    /** Returns all parts. GetAllParts is a getter method for the allParts attribute.
     @return all of the parts in the allParts in an ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Returns all products. GetAllProducts is a getter method for the allProducts attribute.
     @return all of the products in the allParts in an ObservableList
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    /** Determines if string is numeric. Trys to parse a string as a double, thus determining if string is numeric.
     @param strNum string that may contain a numeric value
     @return true is if input string is numeric
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
