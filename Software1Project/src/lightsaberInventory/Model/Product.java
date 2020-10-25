package lightsaberInventory.Model;

import javafx.collections.ObservableList;

/** The Product class defines methods and attributes for Products. */

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts;

    /** Constructor for Product instances.
     @param id system defined
     @param name string name of the product
     @param price price in USD
     @param stock amount held in inventory
     @param min minimum inventory
     @param max maximum inventory
     @param associatedParts parts needed to build the product
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }



    /** Getter Method for ID. Returns the Product ID for a Product instance.
     @return the Product ID.
     */
    public int getId() {
        return id;
    }

    /** Setter Method for ID. Updates the Product ID for a Product instance.
     @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter Method for Name. Returns the Product Name for a Product instance.
     @return The Product Name. Type: String.
     */
    public String getName() {
        return name;
    }

    /** Setter Method for Name. Updates the Product Name for a Product instance.
     @param name The name to set. Types: string.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter Method for Price. Returns the price for a Product instance.
     @return the price of a product.
     */
    public double getPrice() {
        return price;
    }

    /** Setter Method for Price. Updates the Product Price for a Product instance.
     @param price The price to set. Type: double.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter Method for stock. Returns the stock for a Product instance.
     @return the stock of a product.
     */
    public int getStock() {
        return stock;
    }

    /** Setter Method for stock. Updates the Product stock levels for a Product instance.
     @param stock the stock to set. Type: int.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter Method for Min. Returns the stock minimum for a Product instance.
     @return the max of a product.
     */
    public int getMin() {
        return min;
    }

    /** Setter Method for min. Updates the inventory minimum for a Product instance.
     @param min the min to set. Type: int.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter Method for ID. Returns the inventory max for a Product instance.
     @return the max
     */
    public int getMax() {
        return max;
    }

    /** Setter Method for stock. Updates the inventory max levels for a Product instance.
     @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Associated a part with a product. The addAssociatedPart method adds a part to a Product object's associated parts.
     @param part part instance to be attached. Type: part.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    };


    /** Removes a part association from a product. The deleteAssociatedPart method detaches a part from a Product object.
     @param selectedAssociatedPart part instance to be detached. Type: Part.
     @return true if part is deleted, false if part not deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        int id = selectedAssociatedPart.getId();
        boolean returnVal = false;
        for(int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == id) {
                associatedParts.remove(i);
                returnVal = true;
            }
        }
        return returnVal;
    };


    /** Returns associated parts for a product. GetAllAssociatedParts method fetches an array a Product object's associated parts.
     @return an ObservableList of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
