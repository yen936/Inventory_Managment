package lightsaberInventory.Model;

/** Outsource class extends the abstract Part class, adding "companyName" attribute and defining methods.
 @author Benji Magnelli by the grace of God
 */

public class Outsourced extends Part {

    private String companyName;

    /** Constructor for Outsource Instance. Constructor extends the abstract Part class adding "companyName" attribute.
     @param id system defined
     @param name string name of the product
     @param price price in USD
     @param stock amount held in inventory
     @param min minimum inventory
     @param max maximum inventory
     @param companyName name of firm subcontracted to build the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    };

    /** Setter method for companyName. SetCompanyName updates machineID for Outsourced Parts.
     @param companyName name of firm subcontracted to build the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Getter method for companyName. GetCompanyName returns machineID for Outsourced Parts.
     @return the name of firm subcontracted to build the part
     */
    public String getCompanyName() {
        return companyName;
    };


}
